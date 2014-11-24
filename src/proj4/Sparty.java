package proj4;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import proj4.*;


public class Sparty extends Sprite
{
    Sparty() throws SlickException
    {
    super();
	  Image [] standing = {new Image("data/SpartyIdle1.png"), new Image("data/SpartyIdle2.png")};
    Image [] tired = {new Image("data/SpartyMoved1.png"), new Image("data/SpartyMoved2.png")};
    Image [] grabbed = {new Image("data/SpartySelected1.png"), new Image("data/SpartySelected2.png")};
    Image [] translate = {new Image("data/SpartyMoving1.png"), new Image("data/SpartyMoving2.png")};

    int [] StandingDuration = {800, 800};
      
    idle = new Animation(standing, StandingDuration, true);
    moved = new Animation(tired, StandingDuration, true);
    selected = new Animation(grabbed, StandingDuration, true);
    moving = new Animation(translate, StandingDuration, false);
    
    sprite = idle;
      
        health = 38;
        attack = 12;
        defense = 7;
        bounds = 4;
        moves_left = 4;
        available = true;
        friendly = false;
        accuracy = 70;
        avoid = 10;
    }
    
    public String getType()
    {
      return "Sparty";
    }
    
  public void draw()
  {
    sprite.draw(x, y);
  }

    public boolean isFriendly()
    {
        return friendly;
    }

    public int getHealth()
    {
       return health;
    }

    public void setHealth(int inHealth)
    {
        health -= inHealth;
        if (health < 0)
        {
          health = 0;
        }
    }

    public int getAttack()
    {
        return attack;
    }

    public int getDefense()
    {
        return defense;
    }

    public boolean isAvailable()
    {
        return available;
    }

    public void setAvailable(boolean canMove)
    {
        available = canMove;
    }

    public int getBounds()
    {
        return bounds;
    }

     public int getAccuracy()
    {
        return accuracy;
    }

    public int getAvoid()
    {
        return avoid;
    }
}