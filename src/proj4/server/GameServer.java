package proj4.server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import static java.lang.System.out;

public class GameServer
{
  private final int portNum;
  private Socket socket;
  private DataOutputStream outData;
  private DataInputStream inData;
  private final Database database;
  
  public GameServer(int inPortNum)
  {
    portNum = inPortNum;
    inData = null;
    outData = null;
    socket = null;
    database = new Database();
  }
  
  public void startServer()
  {
    ServerSocket serverSock;
    try
    {
      serverSock = new ServerSocket(portNum);
      
      out.println("----------Network Info----------");
      Enumeration<NetworkInterface> nInterfaces = NetworkInterface.getNetworkInterfaces();
      while (nInterfaces.hasMoreElements())
      {
        Enumeration<InetAddress> inetAddresses = nInterfaces.nextElement().getInetAddresses();
        while (inetAddresses.hasMoreElements())
        {
          String address = inetAddresses.nextElement().getHostAddress();
          out.println(address);
        }
      }
      out.println("--------------------------------\n");
      out.println("Waiting for client to connect...");
      socket = serverSock.accept();
      outData = new DataOutputStream(socket.getOutputStream());
      inData = new DataInputStream(socket.getInputStream());
      out.println("Client connection accepted");
    }
    catch (IOException ioe)
    {
      out.println("ERROR: Caught exception starting server");
      System.exit(7);
    }
  }
  
  public boolean sendString(String strToSend)
  {
    boolean success;
    try
    {
      outData.writeBytes(strToSend);
      outData.writeByte(0); //send 0 to signal the end of the string
      success = true;
    }
    catch (IOException e)
    {
      System.out.println("Caught IOException Writing To Socket Stream!");
      System.exit(-1);
      success = false;
    }
    return (success);
  }
  
  public String recvString()
  {
    Vector< Byte > byteVec = new Vector< Byte >();
    byte [] byteAry;
    byte recByte;
    String receivedString = "";
    try
    {
      recByte = inData.readByte();
      while (recByte != 0)
      {
        byteVec.add(recByte);
        recByte = inData.readByte();
      }
      byteAry = new byte[byteVec.size()];
      for (int ind = 0; ind < byteVec.size(); ind++)
      {
        byteAry[ind] = byteVec.elementAt(ind).byteValue();
      }
      receivedString = new String(byteAry);
    }
    catch (IOException ioe)
    {
      out.println("ERROR: receiving string from socket");
      ioe.printStackTrace();
      System.exit(8);
      }
    return (receivedString);
  }
  
  public String readString(String inStr)
  {
    Scanner sc;
    sc = new Scanner(inStr);
    String type = sc.next();
    String msg;
    switch (type)
    {
      case "ADDPLAYER":
        msg = database.addPlayer(sc.nextLine());
        break;
      case "GETPLAYER":
        String name = sc.next();
        if (!database.hasPlayer(name))
        {
          msg = "Player does not exist.";
          break;
        }
        msg = database.getPlayer(name).getData();
        break;
      case "SAVEPLAYER":
        String user = sc.next();
        if (!database.hasPlayer(user))
        {
          msg = "Player does not exist. Save unsuccessful.";
          break;
        }
        msg = database.savePlayer(user + sc.nextLine());
        break;
      case "DOESPLAYEREXIST":
        msg = String.valueOf(database.hasPlayer(sc.next()));
        break;
      case "EXITSERVER":
        msg = "Exiting server...";
        break;
      default:
        msg = "Invalid type.";
        break;
    }
    sc.close();
    database.saveDatabase();
    return msg;
  }
}
