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
      out.println(theClient.addPlayer("newPlayer"));
      out.println(theClient.getFriendlyString("newPlayer"));
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