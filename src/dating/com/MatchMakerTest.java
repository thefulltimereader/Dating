package dating.com;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class MatchMakerTest {
  MatchMaker m;
  int n;
  Person p;
  List<Double> person;
  @Before
  public void setUp() throws Exception {
    n=new Random().nextInt(19)+3;
    m = new MatchMaker(n);
    p = new Person(n, "person.txt");
    person = new ArrayList<Double>();
  }
  public void generateData(){
    Random rand = new Random();
    StringBuilder str = new StringBuilder();
    List<Double> randomCandidate = new ArrayList<Double>();
    // generate 20 random candidates
    double score = 0;
    int temp;
    person = p.getWeights();
    for (int i = 0; i < 20; i++) {
      for (int k = 0; k < n; k++) {
        temp = (int) (rand.nextDouble() * 100);// round off to two
        randomCandidate.add(k, ((double) temp) / 100);
      }
      str.append(randomCandidate.get(0));
      score = 0;
      for (int k = 0; k < n; k++) {
        score = score + (randomCandidate.get(k) * person.get(k));
        if (k > 0)
          str.append(":").append(randomCandidate.get(k));
      }
      temp = (int) (score * 100);
      score = ((double) temp) / 100;
      str.insert(0,":").insert(0, score);
     System.out.println(str);
      m.saveData(str.toString(), i);
      str = new StringBuilder();
    }
    
  }
  @Test
  public void testSaveData(){
    Random rand = new Random();
    StringBuilder str = new StringBuilder();
    for(int i=0;i<n;i++){
      int temp = (int) (rand.nextDouble() * 100);// round off to two
      str.append((double) temp / 100).append(":");
    }
    m.saveData(str.toString(), 0);
  }
  //server simulator
  @Test
  public void testGetCandidate(){
    List<Double> clientCandidate = new ArrayList<Double>();
    generateData();
    //start game
    for(int cCount=0;cCount<20; cCount++){
    String nextC = m.getCandidate();
    int temp=0; 
    double[] cScore = new double[20];
    double totalScore = 0; 
    String[] parse = nextC.split(":");
    for (int i = 0; i < n; i++) {
      clientCandidate.add(i, Double.parseDouble(parse[i]));
    }
    cScore[cCount] = 0;
    for (int i = 0; i < person.size(); i++) {
      cScore[cCount] += (clientCandidate.get(i) * person.get(i));
    }
    temp = (int) (cScore[cCount] * 100);
    cScore[cCount] = ((double) temp) / 100;
    totalScore += cScore[cCount];
    temp = (int) (totalScore * 100);
    totalScore = ((double) temp) / 100;
    if (cScore[cCount] == 1) {
       System.out.println("IDEAL CANDIDATE FOUND");
       break;
    }
    if (cCount == 19) {
      System.out.println("NO MORE CANDIDATES");break;
    }
    m.scoreBack("SCORE:" + cScore[cCount] + ":" + totalScore + ":"
              + (cCount + 1));
  }
  }

}
