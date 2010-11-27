package dating.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {

  public static void main(String[] args) throws Exception {
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    List<Double> candidate = new ArrayList<Double>();
    String ID = args[0];

    try {
      socket = new Socket("localhost", 20000);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (UnknownHostException e) {
      System.out.println("Unknown host");
      System.exit(-1);
    } catch (IOException e) {
      System.out.println("No I/O");
      System.exit(-1);
    }

    String inputLine;
    String[] parse;
    int N = 0;

    out.println(ID);

    while ((inputLine = in.readLine()) != null && ID.equals("Person")) {
      System.out.println("" + inputLine);
      if (inputLine.startsWith("N:")) {
        parse = inputLine.split(":");
        N = Integer.parseInt(parse[1]);
        String fName = args.length > 2 ? args[1] : "Person.txt";
        Person p = new Person(N, fName);
        p.writeToFile();
        out.println(fName);// out.println("Person.txt");
      }

      if (inputLine.equals("VALID ATTRIBUTES")
          || inputLine.equals("INVALID ATTRIBUTES")) {
        inputLine = in.readLine();
        if (inputLine.equals("DISCONNECT"))
          break;
      }

    }
MatchMaker m = null;
    while ((inputLine = in.readLine()) != null && ID.equals("Matchmaker")) {
      System.out.println("" + inputLine);
      if (inputLine.startsWith("N:")) {
        parse = inputLine.split(":");
        N = Integer.parseInt(parse[1]);
        m = new MatchMaker(N);
        System.out.println("save information..");
        for (int i = 0; i < 20; i++) {
          inputLine = in.readLine();
          System.out.println(inputLine);
          m.saveData(inputLine, i);
        }
        m.printMat();
      }
      else if (inputLine.startsWith("SCORE:")) {
        m.scoreBack(inputLine);
        String candList = m.getCandidate();
        out.println(candList);
      }
      else if (inputLine.equals("DISCONNECT"))
        break;

      else if (inputLine.equals("IDEAL CANDIDATE FOUND")
          || inputLine.equals("NO MORE CANDIDATES")) {
        inputLine = in.readLine();
        System.out.println("" + inputLine);
        break;
      }

    }

    out.close();
    in.close();
    socket.close();
  }

}
