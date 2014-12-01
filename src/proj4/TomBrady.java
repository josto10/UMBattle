package proj4;

import org.newdawn.slick.SlickException;

public class TomBrady extends Sprite
{
    TomBrady() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 26;
        attack = 15;
        defense = 5;
        bounds = 3;
        moves_left = 3;
        available = true;
        friendly = true;
        accuracy = 170;
        avoid = 15;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "TomBrady";
    }
}