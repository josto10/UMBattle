package proj4;

import org.newdawn.slick.SlickException;

public class ConnorCook extends Sprite
{
    ConnorCook() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 32;
        attack = 10;
        defense = 6;
        bounds = 0;
        moves_left = 0;
        available = true;
        friendly = false;
        accuracy = 110;
        avoid = 35;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

   public String getType()
    {
        return "ConnorCook";
    }
}