package proj4;

import org.newdawn.slick.SlickException;

public class LouAnnaSimon extends Sprite
{
    LouAnnaSimon() throws SlickException
    {
        super();
      
        // Set Attributes
        health = 34;
        attack = 12;
        defense = 6;
        bounds = 0;
        moves_left = 0;
        available = true;
        friendly = false;
        accuracy = 85;
        avoid = 30;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "LouAnnaSimon";
    }
}