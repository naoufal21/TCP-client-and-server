
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.*;

import java.util.ArrayList;
import java.util.List;



public class tcp1ser {

  // constructors for the packet and the socket 

  public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException {
    int acul = 0;
    List<Integer> mylist = new ArrayList<Integer>();
    if (args.length != 1) {
      System.out.print("correct format: <port number>");
      System.exit(0);
    }
    int port = Integer.parseInt(args[0]);
    ServerSocket serverSocket = new ServerSocket(port);
    acul = 0;
    while (true) {
        try{
        Socket message = serverSocket.accept();
        
        ObjectInputStream numbers = new ObjectInputStream(message.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream ( message.getOutputStream());
        
         
      while(true){  
       mylist= (List<Integer>) numbers.readObject();
      int length = mylist.size();
      for (int i = 0; i < length ; i++) {
        acul = acul + mylist.get(i);
        System.out.println("acul:" + acul);
      }
        
        output.writeInt(acul);
        output.flush(); }
      } catch (IOException e) {
        System.out.println("client disconnected");
        acul = 0;
       continue;
      }
    }
    }

  
}



