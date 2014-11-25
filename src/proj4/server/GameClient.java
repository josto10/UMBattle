package proj4.server;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.newdawn.slick.Game;

import static java.lang.System.out;
import proj4.Sprite;

public class GameClient
{
  private String ipAddr;
  private int portNum;
  private Socket socket;
  private DataOutputStream outData;
  private DataInputStream inData;
  
  public GameClient(String inIPAddr, int inPortNum)
  {
    ipAddr = inIPAddr;
    portNum = inPortNum;
    inData = null;
    outData = null;
    socket = null;
  }
  
  public void startClient()
  {
    try
    {
      socket = new Socket(ipAddr, portNum);
      outData = new DataOutputStream(socket.getOutputStream());
      inData = new DataInputStream(socket.getInputStream());
    }
    catch (IOException ioe)
    {
      out.println("ERROR: Unable to connect - " +
                  "is the server running?");
      System.exit(10);
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
  
  public String savePlayer(String name, String data)
  {
    sendString("SAVEPLAYER " + name + " " + data);
    return recvString();
  }
  
  public String getPlayer(String name)
  {
    sendString("GETPLAYER " + name);
    return recvString();
  }
  
  public boolean doesPlayerExist(String name)
  {
    sendString("DOESPLAYEREXIST " + name);
    Scanner sc = new Scanner(recvString());
    return sc.nextBoolean();
  }
  
  public String getEnemyString(String name)
  {
    String temp = getPlayer(name);
    Scanner sc = new Scanner(temp);
    
    for (int i = 0; i < 2; ++i)
    {
      sc.next();
    }
    
    while(sc.hasNext())
    {
      String guyType = sc.next();
      boolean [] guyBools = new boolean[Sprite.NUM_BOOLS];
      int [] guyVars = new int[Sprite.NUM_VARIABLES];

      for (int i = 0; i < Sprite.NUM_BOOLS; ++i)
      {
        guyBools[i] = sc.nextBoolean();
      }
      for (int i = 0; i < Sprite.NUM_VARIABLES; ++i)
      {
        guyVars[i] = sc.nextInt();
      }

      if (guyBools[1] == false)
      {
        String out = guyType;
        for (int i = 0; i < Sprite.NUM_BOOLS; ++i)
        {
          out += " " + guyBools[i];
        }
        for (int i = 0; i < Sprite.NUM_VARIABLES; ++i)
        {
          out += " " + guyVars[i];
        }
        while (sc.hasNext())
        {
          out += " " + sc.next();
        }
        sc.close();
        sc = new Scanner(out);
        int numEnemies = 0;
        while (sc.hasNext())
        {
          for (int i = 0; i < Sprite.NUM_BOOLS + Sprite.NUM_VARIABLES + 1; ++i)
          {
            sc.next();
          }
          ++numEnemies;
        }
        
        return numEnemies + " " + out;
      }
    }
    return "";
  }
  
  public String getFriendlyString(String name)
  {
    String temp = getPlayer(name);
    Scanner sc = new Scanner(temp);
    
    for (int i = 0; i < 2; ++i)
    {
      sc.next();
    }
    
    String out = "";
    while(sc.hasNext())
    {
      String guyType = sc.next();
      boolean [] guyBools = new boolean[Sprite.NUM_BOOLS];
      int [] guyVars = new int[Sprite.NUM_VARIABLES];

      for (int i = 0; i < Sprite.NUM_BOOLS; ++i)
      {
        guyBools[i] = sc.nextBoolean();
      }
      for (int i = 0; i < Sprite.NUM_VARIABLES; ++i)
      {
        guyVars[i] = sc.nextInt();
      }
      
      if (guyBools[1] == true)
      {
        if (!out.equals(""))
        {
          out += " ";
        }
        out += guyType;
        for (int i = 0; i < Sprite.NUM_BOOLS; ++i)
        {
          out += " " + guyBools[i];
        }
        for (int i = 0; i < Sprite.NUM_VARIABLES; ++i)
        {
          out += " " + guyVars[i];
        }
      }
    }
    sc.close();
    sc = new Scanner(out);
    int numFriendlies = 0;
    while (sc.hasNext())
    {
      for (int i = 0; i < Sprite.NUM_BOOLS + Sprite.NUM_VARIABLES + 1; ++i)
      {
        sc.next();
      }
      ++numFriendlies;
    }
    return numFriendlies + " " + out;
  }
  
  public String exitServer()
  {
    sendString("EXITSERVER");
    return recvString();
  }
}