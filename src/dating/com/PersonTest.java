package dating.com;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testPerson() {
    int n = new Random().nextInt(100);
    System.out.println("N: "+n);
    Person p = new Person(n, "ajPerson.txt");
    p.writeToFile();
  }

}
