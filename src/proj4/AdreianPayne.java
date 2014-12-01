package proj4;

import org.newdawn.slick.SlickException;

public class AdreianPayne extends Sprite
{
    AdreianPayne() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 40;
        attack = 17;
        defense = 3;
        bounds = 0;
        moves_left = 0;
        available = true;
        friendly = false;
        accuracy = 120;
        avoid = 50;
    }
  
  public void draw()
  {
        sprite.draw(x, y);
  }
  
  public String getType()
  {
        return "AdreianPayne";
  }
}