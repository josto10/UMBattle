package proj4;

import org.newdawn.slick.SlickException;

public class MarkDantonio extends Sprite
{
    MarkDantonio() throws SlickException
    {
        super();
    
        // Set Attributes 
        health = 52;
        attack = 14;
        defense = 10;
        bounds = 0;
        moves_left = 0;
        available = true;
        friendly = false;
        accuracy = 95;
        avoid = 25;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "MarkDantonio";
    }
}