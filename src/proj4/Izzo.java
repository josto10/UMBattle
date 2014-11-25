package proj4;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import proj4.*;

public class Izzo extends Sprite
{

  Izzo() throws SlickException
  {
    super();
    Image[] standing =
    {
      new Image("data/fire.png"), new Image("data/fire.png")
    };
    Image[] tired =
    {
      new Image("data/fire.png"), new Image("data/fire.png")
    };
    Image[] grabbed =
    {
      new Image("data/fire.png"), new Image("data/fire.png")
    };
    Image[] translate =
    {
      new Image("data/fire.png"), new Image("data/fire.png")
    };

    int[] StandingDuration =
    {
      800, 800
    };

    idle = new Animation(standing, StandingDuration, true);
    moved = new Animation(tired, StandingDuration, true);
    selected = new Animation(grabbed, StandingDuration, true);
    moving = new Animation(translate, StandingDuration, false);

    sprite = idle;

    health = 45;
    attack = 20;
    defense = 5;
    bounds = 0;
    moves_left = 0;
    available = true;
    friendly = false;
    accuracy = 50;
    avoid = 5;
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

  public String getType()
  {
    return "Izzo";
  }
}
