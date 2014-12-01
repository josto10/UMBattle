package proj4;

import org.newdawn.slick.SlickException;

public class StephenMRoss extends Sprite
{
    StephenMRoss() throws SlickException
    {
        super();
        
        // Set Attributes
        health = 23;
        attack = 7;
        defense = 7;
        bounds = 2;
        moves_left = 2;
        available = true;
        friendly = true;
        accuracy = 80;
        avoid = 50;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "StephenMRoss";
    }
}