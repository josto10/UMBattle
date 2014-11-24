package proj4.server;

import static java.lang.System.out;

import java.util.Scanner;

public class ClientDemo
{
  public static void main(String [] args)
  {
    GameClient theClient;
    String recvdStr;
    Scanner reader = new Scanner(System.in);
    
    theClient = new GameClient("127.0.0.1", 45000);
    theClient.startClient();
    while (true)
    {
      out.print("Enter here: ");
//      send = reader.nextLine();
//      theClient.sendString(send);
//      recvdStr = theClient.recvString();
//      out.println("Received this message from server: " + recvdStr);
      
      out.println(theClient.getPlayer("jmcogan"));
      out.println(theClient.getFriendlyString("jmcogan"));
      out.println(theClient.getEnemyString("jmcogan"));
      recvdStr = (theClient.exitServer());
      if (recvdStr.equals("Exiting server..."))
      {
        out.println("Closing client...");
        reader.close();
        System.exit(0);
      }
    }
  }
}