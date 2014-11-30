/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj4;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Joey
 */
public class MainMenu extends BasicGameState
{
  // Image for images for buttons
  //Image playNow;
  //Image exitGame;

  Image backGround;

  public MainMenu(int ID)
  {
    super();
  }

  public void init(GameContainer container, StateBasedGame game) throws SlickException
  {
    backGround = new Image("data/mainMenu.png");
  }

  public void enter(GameContainer container, StateBasedGame game) throws SlickException
  {
    this.init(container, game);
  }

  public int getID()
  {
    return 0;
  }

  public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
  {
    backGround.draw(0, 0);
  }

  public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
  {
    int posX = Mouse.getX();
    int posY = Mouse.getY();

    // There will be two "play" buttons:
    // 1) New Game
    // 2) Load Game
    // The Load Game will attempt to contact the server to load player data.
    // potentially offer a choice between internet load and local load. Then
    // the user can play without an internet connection if they have the 
    // save file local on their computer. (obfuscate save so it's hard to
    // hack? -- moderate stretch goal). 
    
    // New Game will lauch and interface to set up a game. The user will
    // create a character and a name for the save files.
    if ((posX > 510 && posX < 760) && (posY > 400 && posY < 465))
    {
      if (Mouse.isButtonDown(0))
      {
        ((UMBattle) sbg).help(0);
        sbg.enterState(1);
      }
    }

    if ((posX > 510 && posX < 760) && (posY > 290 && posY < 355))
    {
      if (Mouse.isButtonDown(0))
      {
        System.exit(0);
      }
    }
  }
}
