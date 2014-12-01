package proj4;

import org.newdawn.slick.SlickException;

public class BradyHoke extends Sprite
{
    BradyHoke() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 47;
        attack = 7;
        defense = 10;
        bounds = 2;
        moves_left = 2;
        available = true;
        friendly = true;
        accuracy = 75;
        avoid = 10;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "BradyHoke";
    }
}