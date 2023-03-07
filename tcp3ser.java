
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
public class tcp3ser {

  // constructors for the packet and the socket 

  public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, NullPointerException{
    int acul = 0;
    int aaacul= 0;
    if (args.length != 1) {
      System.out.print("correct format: <port number>");
      System.exit(0);
    }
    
    int port = Integer.parseInt(args[0]);
    //UDP
    SocketAddress address = new InetSocketAddress(port);
    DatagramChannel channel = DatagramChannel.open();
    DatagramSocket socket = channel.socket();
    socket.bind(address);
    //UDP

    //TCP
    ServerSocketChannel serverSocket =  ServerSocketChannel.open(); 
    SocketAddress porttt = new InetSocketAddress(port);
    serverSocket.socket().bind(porttt);
    //TCP

    serverSocket.configureBlocking(false);
    channel.configureBlocking(false);

    Selector selector = Selector.open();
    
    serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    channel.register(selector, SelectionKey.OP_READ);
    
    

    
    while (true) {
      selector.select();
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      Iterator<SelectionKey> iter = selectedKeys.iterator();
      while (iter.hasNext()) {
              System.out.print("im here");
          SelectionKey key = iter.next();

          if (key.isAcceptable()) {
            System.out.print("im here1");
              SocketChannel message = serverSocket.accept();
              message.configureBlocking(false);
              message.register(selector, SelectionKey.OP_READ);
          }

          else if (key.isReadable()) {
            System.out.print("im here2");
            ByteBuffer buf = ByteBuffer.allocate(256);
            SocketChannel message = (SocketChannel) key.channel();
            acul = 0;
            try{
             
              
              
        
              buf.clear();
              message.read(buf); 
              int numbers=buf.getInt(0);
              acul=acul+numbers;
              System.out.print("\nacul:"+acul);
              buf.flip();
              buf.putInt(0,acul);
              message.write(buf);
             
        }
         catch (IOException | NullPointerException e) {
          System.out.println("client disconnected");
          break;
        }
      
          
          iter.remove();
      
  


    }

           
         
            }
          
  }}}
    
     
    
    
    
   

 
 


    
    

  



