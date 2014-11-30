package proj4;

import java.util.Stack;

import org.newdawn.slick.Animation;
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
    // instantiate movement stack
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

  // should use inherited
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

  /**
   *
   * @return (separated by spaces) 1) type 2) x 3) y 4)
   */
  @Override
  public String toString()
  {
    String outString = "";

    outString += getType() + " ";
    //
    outString += available + " "; //0
    outString += friendly + " "; //1
    outString += isSelected + " "; //2
    //
    outString += (int) x + " "; //0
    outString += (int) y + " "; //1
    outString += health + " "; //2
    outString += attack + " "; //3
    outString += defense + " "; //4
    outString += bounds + " "; //5
    outString += moves_left + " "; //6
    outString += accuracy + " "; //7
    outString += avoid; //8

    return outString;
  }
}
