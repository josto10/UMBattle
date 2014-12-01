package proj4;

import java.util.Scanner;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import proj4.*;

/*
 * @author Alexander
 */

public class LevelMap
{
  private TiledMap currentMap;
  private Sprite[] enemyList;
  private boolean[][] blocked;
  private static Location portal;

  
  public LevelMap()
  {
    
  }
  
  public void init(int levelNum) throws SlickException
  {
    currentMap = new TiledMap(getMapFile(levelNum));

    
    // Populate blocked array
    blocked = new boolean[currentMap.getWidth()][currentMap.getHeight()];
    for (int xAxis=0;xAxis<currentMap.getWidth(); xAxis++)
    {
         for (int yAxis=0;yAxis<currentMap.getHeight(); yAxis++)
         {
             int tileID = currentMap.getTileId(xAxis, yAxis, 0);
             String value = currentMap.getTileProperty(tileID, "Blocked", "false");
             if ("true".equals(value))
             {
                 blocked[xAxis][yAxis] = true;
             }
         }
     }

    initEnemyList(levelNum);
  }
  
  private void initEnemyList(int level) throws SlickException
  {
    // boolean loading = !Game.client.getEnemyString(Game.user).equals("");
    switch (level)
    {
      case 0:
        //portal: 17, 12
        portal = new Location(32f * 17, 32f * 12);
        break;
          
      case 1:
        //portal: 15, 12
        portal = new Location(32f * 15, 32f * 12);
        break;
          
      case 2:
        //portal: 32, 18
        portal = new Location(32f * 32, 32f * 18);
        break;
          
      case 3:
        //portal: 32, 18
        portal = new Location(32f * 32, 32f * 18);
        break;
          
      case 4:
        //portal: 32, 18
        portal = new Location(32f * 32, 32f * 18);
        break;
    }
    
    //if (loading)
    //{
    //loadEnemyList();
    //System.out.println("loading...");
    //return;
    //}
    
    if(level == 0)
    {
      enemyList = new Sprite[3];
      enemyList[0] = new Izzo();
      enemyList[0].setX(32 * 17);
      enemyList[0].setY(32 * 12);
      enemyList[1] = new Sparty();
      enemyList[1].setX(640);
      enemyList[1].setY(320);
      enemyList[2] = new Sparty();
      enemyList[2].setX(640);
      enemyList[2].setY(384);
    }
    else if(level == 1)
    {
      enemyList = new Sprite[1];
      enemyList[0] = new Izzo();
      enemyList[0].setX(15 * 32);
      enemyList[0].setY(12 * 32);
    }
    else if(level == 2)
    {
      enemyList = new Sprite[16];
      enemyList[0] = new Izzo();
      enemyList[0].setX(32 * 32);
      enemyList[0].setY(18 * 32);
      for (int x = 1; x < 15; x++)
      {
        enemyList[x] = new Sparty();
        enemyList[x].setX(2 * 32);
        enemyList[x].setY(x * 32);
      }
    }
    else if(level == 3)
    {
      enemyList = new Sprite[16];
      enemyList[0] = new Izzo();
      enemyList[0].setX(32 * 32);
      enemyList[0].setY(18 * 32);
      for (int x = 1; x < 15; x++)
      {
        enemyList[x] = new Sparty();
        enemyList[x].setX(2 * 32);
        enemyList[x].setY(x * 32);
      }
    }
    else if(level == 4)
    {
      enemyList = new Sprite[16];
      enemyList[0] = new Izzo();
      enemyList[0].setX(32 * 32);
      enemyList[0].setY(18 * 32);
      for (int x = 1; x < 15; x++)
      {
        enemyList[x] = new Sparty();
        enemyList[x].setX(2 * 32);
        enemyList[x].setY(x * 32);
      }
    }
  }
  
  public String getMapFile(int level)
  {
    if(level == 0)
    {
      return "data/Diag.tmx";
    }
    else if(level == 1)
    {
      return "data/BigHouse.tmx";
    }
    else if(level == 2)
    {
      return "data/CCLittle.tmx";
    }
    else if(level == 3)
    {
      return "data/CCLittle.tmx";
    }
    else if(level == 4)
    {
      return "data/CCLittle.tmx";
    }
    
    return null;
  }
  
  public Sprite[] getEnemyList()
  {
    return enemyList;
  }
  
  public void render()
  {
    currentMap.render(0, 0);
  }
  
  public boolean isBlocked(float x, float y)
  {
    int xBlock = (int)x / 32;
    int yBlock = (int)y / 32;
    return blocked[xBlock][yBlock];
  }
  
  public Sprite isOccupied(float x, float y, Sprite[] friendlyList)
  {
    x = ((int)x / 32) * 32;
    y = ((int)y / 32) * 32;
    
    for (Sprite character : friendlyList)
    {
        if (character != null)
        {
            if (x == character.getPosX() && y == character.getPosY())
            {
               return character;
            }
        }
    }
    
    for (Sprite enemy : enemyList)
    {
      if (enemy != null)
      {
        if (x == enemy.getPosX() && y == enemy.getPosY())
        {
          return enemy;
        }
      }
      
    }
    
    return null;
  }
  
  public void setEnemyList(Sprite[] input)
  {
    enemyList = input;
  }
  
  public static float getPortalX()
  {
    return portal.x;
  }
  
  public static float getPortalY()
  {
    return portal.y;
  }
  
  public TiledMap getCurrentMap()
  {
    return currentMap;
  }
  
  public void loadEnemyList() throws SlickException
  {
    String enemyString = Game.client.getEnemyString(Game.user);
    Scanner sc = new Scanner(enemyString);
    int numEnemies = sc.nextInt();
    enemyList = new Sprite[numEnemies];
    for (int i = 0; i < numEnemies; ++i)
    {
      switch (sc.next())
      {
        case "Sparty":
          enemyList[i] = new Sparty();
          break;
        case "Izzo":
          enemyList[i] = new Izzo();
          break;
      }
      enemyList[i].setAvailable(sc.nextBoolean());
      sc.next();
      enemyList[i].setSelected(sc.nextBoolean());
      enemyList[i].setX(sc.nextInt());
      enemyList[i].setY(sc.nextInt());
      enemyList[i].setHealth(sc.nextInt());
      sc.next();
      sc.next();
      sc.next();
      enemyList[i].setMovesLeft(sc.nextInt());
      sc.next();
      sc.next();
    }
    sc.close();
  }
}
