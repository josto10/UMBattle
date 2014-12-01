package proj4;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Not Joey
 */
public class SaveScreen extends BasicGameState
{

  Image winner;
  public int nextLevel;

  public SaveScreen(int id)
  {
    super();
  }

  public void init(GameContainer container, StateBasedGame game) throws SlickException
  {
    switch (nextLevel)
    {
        case 1:
            winner = new Image("data/SaveContinueScreen1-2.png");
            break;
        case 2:
            winner = new Image("data/SaveContinueScreen2-3.png");
            break;
        case 3:
            winner = new Image("data/SaveContinueScreen3-4.png");
            break;
        case 4:
            winner = new Image("data/SaveContinueScreen4-5.png");
            break;
    }
  }

  public void enter(GameContainer container, StateBasedGame game) throws SlickException
  {
    this.init(container, game);
  }

  public int getID()
  {
    return 3;
  }

  public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
  {
    winner.draw(0, 0, 1184, 800);
  }

  public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
  {
    int posX = Mouse.getX();
    int posY = Mouse.getY();

    // Save and Continue
    if ((posX > 440 && posX < 745) && ((800 - posY) > 400 && (800 - posY) < 480))
    {
      if (Mouse.isButtonDown(0))
      {
        System.out.println("Save and Continue");
        ((UMBattle) sbg).help(nextLevel, Game.user);
        sbg.enterState(1);
      }
    }
    
    // Save and Quit
    if ((posX > 440 && posX < 745) && ((800 - posY) > 520 && (800 - posY) < 600))
    {
      if (Mouse.isButtonDown(0))
      {
        System.out.println("Save and Quit");
        sbg.enterState(0);
      }
    }
  }
}
