/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj4;

import java.awt.Font;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.*;

/**
 *
 * @author Joey
 */
public class MainMenu extends BasicGameState
{
  Image backGround;
  private TextField nameInput;

  public MainMenu(int ID)
  {
    super();
  }

  public void init(GameContainer container, StateBasedGame game) throws SlickException
  {
    backGround = new Image("data/MainMenu.png");
    
    Font awtFont = new Font("Times New Roman", Font.BOLD, 48);
    TrueTypeFont ttf = new TrueTypeFont(awtFont, false);
   
    nameInput = new TextField(container, ttf, 370, 230, 440, 60);
    nameInput.setTextColor(new Color(255, 255, 255));
    nameInput.setBorderColor(new Color(0,0,0,0.0f));
    nameInput.setBackgroundColor(new Color(0,0,0,0.0f));
    nameInput.setMaxLength(30);
    nameInput.setConsumeEvents(true);
    nameInput.setFocus(true);
    nameInput.setCursorVisible(true);
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
    nameInput.render(gc, g);
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
        Game.iWantToLoad = true;
        ((UMBattle) sbg).help(0, nameInput.getText());
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
