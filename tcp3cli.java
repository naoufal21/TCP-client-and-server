import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class tcp3cli {

  public static void main(String[] args) throws IOException {
    Integer port_numer;
    InetAddress ip_address;
    Scanner scanner = new Scanner(System.in);
    Integer value;
    if (args.length != 2 && args.length != 3) {
      System.out.println("Correct format : ip_address port_number [-u](if you want udp)");
      System.exit(0);
    }
    ip_address = InetAddress.getByName(args[0]);
    port_numer = Integer.parseInt(args[1]);
    if (args.length == 3) {
      if (args[2].equals("-u")) {
      
       
        

        ByteBuffer Buf = ByteBuffer.allocate(256); 

        
         
         
         
         DatagramChannel message= DatagramChannel.open();
        SocketAddress address = new InetSocketAddress(0);
        
        DatagramSocket clientSocket = message.socket();
        clientSocket.bind(address);
        SocketAddress server = new InetSocketAddress(ip_address, port_numer);
        while (true) {
          System.out.println("Please, Enter a number(s) . If multiple, put a space between each number and only put a 0 when you finish (the zero wont be sent or counted) ");
          Integer miaw = 0;
          String words[] = scanner.nextLine().split(" ");
          // gets all numbers into a string array
  
          Integer exception = words.length;
          if (words[0].equals("0")) {
            System.out.print("Exiting...");
            System.exit(0);
          }
          value = Integer.parseInt(words[0]);
        
          Buf.clear();
          Buf.putInt(0,value);
          message.send(Buf, server);
      
          Buf.flip();

          message.receive(Buf);
          int result = Buf.getInt(0);
         System.out.println("Output:" + result);
        
      } }
      //udp case
      else {
        System.out.println("Correct format : ip_address port_number [-u](if you want udp)");
        System.exit(0);
      }
    } else {

      //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      try {
        ByteBuffer buffer = ByteBuffer.allocate(24);
        SocketChannel message = SocketChannel.open();
        message.connect(new InetSocketAddress(ip_address, port_numer));

        //ObjectOutputStream output = new ObjectOutputStream(message.getOutputStream());
        //ObjectInputStream numbers = new ObjectInputStream(message.getInputStream());

        while (true) {
          System.out.println("Please, Enter a number(s) . If multiple, put a space between each number and only put a 0 when you finish (the zero wont be sent or counted) ");
          Integer miaw = 0;
          String[] words = scanner.nextLine().split(" ");
          // gets all numbers into a string array

          Integer exception = words.length;
          if (words[0].equals("0")) {
            System.out.print("Exiting...");
            System.exit(0);
            message.close();
          }

          buffer.clear();

          value = Integer.parseInt(words[0]);
          buffer.putInt(0, value);

          message.write(buffer);

          buffer.flip();

          message.read(buffer);

          int numbers = buffer.getInt(0);

          System.out.println("output:" + numbers);

        }
      } catch (SocketTimeoutException ex) {
        System.out.println("Timeout error \nThere was a problem connecting to the server:" + ip_address);
        System.out.println("please, try again.");
        System.exit(0);
      }
    }
  }
}