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
    nameInput.setFocus(true);
    nameInput.setCursorVisible(true);
    
    int posX = Mouse.getX();
    int posY = Mouse.getY();

    // IN TEXT FIELD
    if ((posX > nameInput.getX() && posX < (nameInput.getX() + nameInput.getWidth())) 
            && ((800 - posY) > nameInput.getY() && (800 - posY) < (nameInput.getY() + nameInput.getHeight())))
    {
      if (Mouse.isButtonDown(0))
      {
        //System.out.print("Hey, ");
        //System.out.println(nameInput.getText());
      }
    }
    
    // new 440, 360
    // load 440, 482
    // quit 440, 592
    
    // width 310, height 80
  
    // NEW
    if ((posX > 440 && posX < 750) && ((800 - posY) > 360 && (800 - posY) < 440))
    {
      if (Mouse.isButtonDown(0))
      {
        System.out.println("New game");
        
        String inText = nameInput.getText();
        
        if (inText.length() > 2)
        {
            Game.isNewGame = true;
            ((UMBattle) sbg).help(0, nameInput.getText());
            sbg.enterState(1);
        } 
      }
    }
    
    // LOAD
    if ((posX > 440 && posX < 750) && ((800 - posY) > 482 && (800 - posY) < 562))
    {
      if (Mouse.isButtonDown(0))
      {
        System.out.println("Load game");
        
        String inText = nameInput.getText();
        
        if (inText.length() > 2)
        {
            Game.isNewGame = false;
            ((UMBattle) sbg).help(0, nameInput.getText());
            sbg.enterState(1);
        }
      }
    }

    // QUIT
    if ((posX > 440 && posX < 750) && ((800 - posY) > 592 && (800 - posY) < 672))
    {
      if (Mouse.isButtonDown(0))
      {
        System.exit(0);
      }
    }
  }
}
