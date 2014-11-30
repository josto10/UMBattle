// EECS 285 Project 4 - UMBattle
package proj4;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class UMBattle extends StateBasedGame
{

  // Game state identifiers

  public static final int MAINMENU = 0;
  public static final int GAME = 1;
  public static final int WINNERSCREEN = 2;
  public static final int SAVESCREEN = 3;

  private TiledMap grassMap;
  private Animation sprite, up, down, left, right;
  private float x = 32f, y = 32f;

    //private boolean[][] blocked;
  //private static final int SIZE = 32;
  public UMBattle()
  {
    super("UMBattle");
  }

  public static void main(String[] arguments)
  {
    try
    {
      AppGameContainer app = new AppGameContainer(new UMBattle());
      app.setDisplayMode(1184, 800, false);
      app.start();
    }
    catch (SlickException e)
    {
      e.printStackTrace();
    }
  }

  public void initStatesList(GameContainer gc) throws SlickException
  {
    this.addState(new MainMenu(MAINMENU));
    this.addState(new Game(GAME));
    this.addState(new WinnerScreen(WINNERSCREEN));
    this.addState(new SaveScreen(SAVESCREEN));
  }

  public void help(int level) throws SlickException
  {
    ((Game) getState(GAME)).initNewLevel(level);
  }

  public void setLevel(int level) throws SlickException
  {
    ((SaveScreen) getState(SAVESCREEN)).nextLevel = level;
  }
}
