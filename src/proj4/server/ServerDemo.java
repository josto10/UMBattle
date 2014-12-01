package proj4.server;

import static java.lang.System.out;

public class ServerDemo
{
  public static void main(String args[])
  {
    GameServer theServer;
    String msg;
    
    theServer = new GameServer(45000);
    theServer.startServer();
    
    while (true)
    {
      msg = theServer.readString(theServer.recvString());
      out.println("Recevied message from client.");
      theServer.sendString(msg);
      out.println("Sending message to client...\n");
      if (msg.equals("Exiting server..."))
      {
        System.exit(0);
      }
    }
  }
}
