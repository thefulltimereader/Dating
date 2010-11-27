package dating.com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Person {
  /**
   * Num of attributes
   */
  private int n;
  private List<Double> pos, neg, w;
  private String fName;
  private Random rand;
  public Person(int n, String fName){
    this.n = n;
    this.fName = fName;
    rand = new Random();
    pos = new ArrayList<Double>();
    neg = new ArrayList<Double>();
    w = new ArrayList<Double>(n);
    generate();
    check();
    write(false);
  }
  private void check(){
    double sum = 0;
    for(Double d: pos){
      sum+=d;
    }
    if(sum!=1) throw new IllegalStateException("pos weight does not sum to 1");
    sum = 0;
    for(Double d: neg){
      sum+=d;
    }
    if(sum!=-1) throw new IllegalStateException("neg weight does not sum to -1");
    w.addAll(pos); w.addAll(neg);
  }
  private void write(boolean w){
    try {
       BufferedWriter out = new BufferedWriter(new FileWriter(fName));
      for(Double d: pos){
        if(w)out.write(d.toString()+"\n");
        else System.out.println(d);
      }
      for(Double d: neg){
        if(w) out.write(d.toString()+"\n");
        else System.out.println(d);
      }
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private void generate() {
    int nPos = rand.nextInt(n);
    int nNeg = n-nPos;
    double negMax = 1/(double)nNeg;
    double posMax = 1/(double)nPos;
    generateList(false, posMax, nPos, pos);
    generateList(true, negMax, nNeg, neg);
  }
  
  private void generateList(boolean neg, double max, int total, List<Double> l) {
    double sum = 0;
    double minimum = max>0.01? 0.01: 0;//to avoid 0 vals(can tho)
    for(int i=0; i<total-1;i++){
      double j= minimum + rand.nextDouble()*max; //rand num between[min, max]
      sum+=j;
      if(neg) l.add(-j);
      else l.add(j);
    }
    if(neg)l.add(-(1-sum));
    else l.add(1-sum);
  }
  public String getFileName() {
    return fName;
  }
  public void writeToFile() {
    write(true);    
  }
  List<Double> getWeights(){
    return Collections.unmodifiableList(w);
  }
  
}
