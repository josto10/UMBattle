package proj4.server;

import java.util.Scanner;

public class PlayerData
{
  private String data;
  
  public PlayerData()
  {
    data = "";
  }
  
  public PlayerData(String dataIn)
  {
    data = dataIn;
  }
  
  public PlayerData(PlayerData copy)
  {
    this(copy.data);
  }
  
  public String getData()
  {
    return data;
  }
  
  public String getName()
  {
    Scanner sc = new Scanner(data);
    String out = sc.next();
    sc.close();
    return out;
  }
  
  public void setData(String inData)
  {
    data = inData;
  }
}
