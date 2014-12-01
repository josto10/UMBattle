package proj4;

import java.util.Stack;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

abstract public class Sprite
{
  public static final int NUM_VARIABLES = 9;
  public static final int NUM_BOOLS = 3;
  protected Animation sprite, idle, moved, moving, selected;
  protected float x = 32f, y = 32f;
  protected Stack<Location> moves;
  protected int health;
  protected int attack;
  protected int defense;
  protected int bounds;
  protected int moves_left;
  protected boolean available;
  protected boolean friendly;
  protected int accuracy;
  protected int avoid;
  protected boolean isSelected;
  public boolean canAttack;

  public Sprite() throws SlickException
  {
    // set up images
    String imageFilePath = "data/" + getType();
      
    Image[] standing =
    {
      new Image(imageFilePath + "Idle1.png"), new Image(imageFilePath + "Idle2.png")
    };
    Image[] tired =
    {
      new Image(imageFilePath + "Moved1.png"), new Image(imageFilePath + "Moved2.png")
    };
    Image[] grabbed =
    {
      new Image(imageFilePath + "Selected1.png"), new Image(imageFilePath + "Selected2.png")
    };
    Image[] translate =
    {
      new Image(imageFilePath + "Moving1.png"), new Image(imageFilePath + "Moving2.png")
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
  
    moves = new Stack<>();
  }

  public void update(long delta)
  {
    if (!available)
    {
      sprite = moved;
    }
    else if (bounds > moves_left)
    {
      sprite = moving;
    }
    else if (isSelected)
    {
      sprite = selected;
    }
    else
    {
      sprite = idle;
    }
    sprite.update(delta);
  }

  public boolean moveToward(float MoveToX, float MoveToY, long delta)
  {
    if (moves.size() > 1)
    {
      Location temp = moves.peek();
      moves.pop();
      if (moves.peek().x == MoveToX && moves.peek().y == MoveToY)
      {
        moves.pop();
        this.setMovesLeft(this.getMovesLeft() + 2);
      }
      else
      {
        moves.push(temp);
      }
    }

    if (this.getMovesLeft() == 0)
    {
      return false;
    }

    if (MoveToX != x)
    {
      if (((MoveToX - x) < 1 && (MoveToX - x) > -1)
              || (x - MoveToX) < 1 && (x - MoveToX) > -1)
      {
        x = MoveToX;
        this.setMovesLeft(this.getMovesLeft() - 1);
        pushMoves(new Location(MoveToX, MoveToY));
        //******************************************* Set Available? *****//
        if (this.getMovesLeft() == 0)
        {
          this.setAvailable(false);
        }
      }

      if (MoveToX > x)
      {
        sprite = moving;
        x += delta * 0.2f;
        sprite.update(delta);
        if (LevelMap.getPortalX() == MoveToX && LevelMap.getPortalY() == MoveToY)
        {
          return true;
        }
        return false;
      }
      if (MoveToX < x)
      {
        sprite = moving;
        x -= delta * 0.2f;
        sprite.update(delta);
        if (LevelMap.getPortalX() == MoveToX && LevelMap.getPortalY() == MoveToY)
        {
          return true;
        }
        return false;
      }
    }

    if (MoveToY != y)
    {
      // move to y
      if (((MoveToY - y) < 1 && (MoveToY - y) > -1)
              || (y - MoveToY) < 1 && (y - MoveToY) > -1)
      {
        y = MoveToY;
        this.setMovesLeft(this.getMovesLeft() - 1);
        pushMoves(new Location(MoveToX, MoveToY));
        if (this.getMovesLeft() == 0)
        {
          this.setAvailable(false);
        }
      }

      if (MoveToY > y)
      {
        sprite = moving;
        y += delta * 0.2f;
        sprite.update(delta);
        if (LevelMap.getPortalX() == MoveToX && LevelMap.getPortalY() == MoveToY)
        {
          return true;
        }
        return false;
      }
      if (MoveToY < y)
      {
        sprite = moving;
        y -= delta * 0.2f;
        sprite.update(delta);
        if (LevelMap.getPortalX() == MoveToX && LevelMap.getPortalY() == MoveToY)
        {
          return true;
        }
        return false;
      }
    }
    return false;
  }
  
  abstract public void draw();

  public float getPosX()
  {
    return x;
  }

  public float getPosY()
  {
    return y;
  }

  public int getHealth()
  {
    return health;
  }

  public void setX(float newX)
  {
    x = newX;
  }

  public void setY(float newY)
  {
    y = newY;
  }

  public void setHealth(int inHealth)
  {
    health -= inHealth;
    if (health < 0)
    {
      health = 0;
    }
  }
  
  public void initHealth(int inHealth)
  {
    health = inHealth;
  }

  public boolean isAvailable()
  {
    return available;
  }

  public void setAvailable(boolean canMove)
  {
    available = canMove;
  }

  public int getMovesLeft()
  {
    return moves_left;
  }

  public void setMovesLeft(int moves)
  {
    moves_left = moves;
  }

  public void setSelected(boolean selected)
  {
    isSelected = selected;
  }

  public int getAttack()
  {
    return attack;
  }

  public int getDefense()
  {
    return defense;
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

  public boolean isFriendly()
  {
    return friendly;
  }

  public void pushMoves(Location loc)
  {
    moves.push(loc);
  }

  public int getSizeMoves()
  {
    return moves.size();
  }

  public void popMoves()
  {
    moves.pop();
  }

  public Location peekMoves()
  {
    return moves.peek();
  }

  public void resetMoves()
  {
    while (!moves.empty())
    {
      moves.pop();
    }
  }

  abstract public String getType();

  //@return (separated by spaces) 1) type 2) x 3) y 4)
   
  @Override
  public String toString()
  {
    String outString = "";

    outString += getType() + " ";
    outString += available + " "; 
    outString += friendly + " "; 
    outString += isSelected + " "; 
    outString += (int) x + " "; 
    outString += (int) y + " "; 
    outString += health + " "; 
    outString += attack + " "; 
    outString += defense + " "; 
    outString += bounds + " "; 
    outString += moves_left + " "; 
    outString += accuracy + " "; 
    outString += avoid; 

    return outString;
  }
}
