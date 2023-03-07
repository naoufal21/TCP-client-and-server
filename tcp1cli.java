import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class tcp1cli {

  public static void main(String[] args) throws IOException {
    Integer port_numer;
    InetAddress ip_address;
    Scanner scanner = new Scanner(System.in);
    Integer value;
    if (args.length != 2 || Integer.parseInt(args[1]) == 0) {
      System.out.println("Correct format : ip_address port_number");
      System.exit(0);
    } else if (args[1].equals("0")) {
      System.out.println("port number 0 doesnt exist ");
      System.out.println("Exiting..");
      System.exit(0); // checks the arguments and makes sure the format is correct
    }
    ip_address = InetAddress.getByName(args[0]);
    port_numer = Integer.parseInt(args[1]);
   
    Socket message = new Socket(ip_address, port_numer);
    ObjectOutputStream output = new ObjectOutputStream(message.getOutputStream());
    ObjectInputStream numbers = new ObjectInputStream(message.getInputStream());

    while (true) {
      System.out.println("Please, Enter a number(s) . If multiple, put a space between each number and only put a 0 when you finish (the zero wont be sent or counted) ");
      Integer miaw = 0;
      String[] words = scanner.nextLine().split(" ");
      // gets all numbers into a string array
       List < Integer > mylist = new ArrayList < Integer > ();
      Integer exception = words.length;
      if (words[0].equals("0")) {
        System.out.print("Exiting...");
        System.exit(0);
        message.close();
      }
      for (int i = 0; i < words.length; i++) {

        if (words[i].equals("0")) {
          exception = i;
          break;
        }
        mylist.add(Integer.parseInt(words[i]));
      }
      value = Integer.parseInt(words[miaw]);
      output.writeObject(mylist);
      output.flush();

      
      int character = numbers.readInt();
      System.out.println("output:" + character);

    }

  }

}