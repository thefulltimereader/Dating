package dating.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchMaker {
  private int n;
  private List<List<Double>> dataMatrix;
  private List<Double> candidate;
  public MatchMaker(int n){
    this.n = n;
    dataMatrix = new ArrayList<List<Double>>(40);
    candidate = new ArrayList<Double>(n);
    //preallocate memory
    for(int i=0; i<20;i++)
      dataMatrix.add(new ArrayList<Double>(n));
  }
  public void saveData(String str, int ind){
    String[] scorestr = str.split(":");
    List<Double> arr = dataMatrix.get(ind);
    for(int i=0; i<scorestr.length; i++){
      arr.add(Double.parseDouble(scorestr[i]));
    }
  }
  public void printMat(){
    for(List<Double> l : dataMatrix){
      for(Double d: l)
       System.out.print(d+" ");
      System.out.println();
    }
  }
  
  public String getCandidate(){
    Random generator = new Random();
    candidate.clear();
    for (int k = 0; k < n; k++) {
      int temp = (int) (generator.nextDouble() * 100);
      candidate.add(((double) temp) / 100);
    }

    return formatCandidate();
  }
  
  private String formatCandidate(){
    StringBuilder str = new StringBuilder().append(candidate.get(0));
    for (int k = 1; k < n; k++) 
          str.append(":").append(candidate.get(k));
    System.out.println("Next candidate: "+str);
    return str.toString();
  }
  void scoreBack(String str){
    String[] s = str.split(":");
    
  }
}
