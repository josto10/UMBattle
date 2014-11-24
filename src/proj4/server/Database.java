package proj4.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.out;

public class Database
{
  private static String DATABASE_PATH = "src/database.txt";
  private PlayerData [] players;
  
  public Database()
  {
    ////////////////////////////////////////////////////////////////////////////
    if (!(new File(DATABASE_PATH).isFile()))
    {
      out.println("file not found");
      File databaseFile = new File(DATABASE_PATH);
      try
      {
        databaseFile.createNewFile();
      }
      catch( IOException e1 )
      {
        out.println("couldn't create new file");
        e1.printStackTrace();
      }
    }
    ////////////////////////////////////////////////////////////////////////////
    Scanner sc;
    int numPlayers = 0;
    try
    {
      sc = new Scanner(new File(DATABASE_PATH));
      while (sc.hasNextLine())
      {
        ++numPlayers;
        sc.nextLine();
      }
      players = new PlayerData[numPlayers];
      sc.close();
      
      sc = new Scanner(new File(DATABASE_PATH));
      for (int i = 0; i < numPlayers; ++i)
      {
        players[i] = new PlayerData(sc.nextLine());
      }
      sc.close();
    }
    catch( FileNotFoundException e1 )
    {
      out.println("file not found");
      e1.printStackTrace();
    }
  }
  
  public PlayerData getPlayer(int playerID)
  {
    return players[playerID];
  }
  
  public PlayerData getPlayer(String name)
  {
    for (int i = 0; i < players.length; ++i)
    {
      Scanner sc = new Scanner(players[i].getData());
      if (sc.next().equals(name))
      {
        sc.close();
        return players[i];
      }
      sc.close();
    }
    return null;
  }
  
  public int getPlayerID(String player)
  {
    for (int i = 0; i < players.length; ++i)
    {
      if (players[i].getName().equals(player))
      {
        return i;
      }
    }
    return -1;
  }
  
  public boolean hasPlayer(String player)
  {
    for (int i = 0; i < players.length; ++i)
    {
      Scanner sc = new Scanner(players[i].getData());
      if (sc.next().equals(player))
      {
        sc.close();
        return true;
      }
      sc.close();
    }
    return false;
  }
  
  public String addPlayer(String inData)
  {
    if (hasPlayer(inData))
    {
      return ("Username taken. Player not created.");
    }
    growPlayers();
    players[players.length - 1] = new PlayerData(inData);
    return ("Successfully added player.");
  }
  
  public void saveDatabase()
  {
    BufferedWriter wr = null;
    try
    {
      wr = new BufferedWriter(new FileWriter(DATABASE_PATH));
      for (int i = 0; i < players.length; ++i)
      {
        wr.write(players[i].getData() + "\n");
      }
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        wr.close();
      }
      catch( IOException e )
      {
        e.printStackTrace();
      }
    }
  }
  
  public String savePlayer(String in)
  {
    Scanner sc = new Scanner(in);
    getPlayer(sc.next()).setData(in);
    sc.close();
    return "Successfully saved player.";
  }
  
  public String toString()
  {
    String out = "";
    for (int i = 0; i < players.length; ++i)
    {
      out += players[i].getData() + "\n";
    }
    return out;
  }
  
  private void growPlayers()
  {
    PlayerData newPlayers [] = new PlayerData[players.length + 1];
    for (int i = 0; i < players.length; ++i)
    {
      newPlayers[i] = new PlayerData(players[i]);
    }
    players = newPlayers;
  }
}
