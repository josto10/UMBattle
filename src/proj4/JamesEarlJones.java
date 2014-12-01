package proj4;

import org.newdawn.slick.SlickException;

public class JamesEarlJones extends Sprite
{
    JamesEarlJones() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 23;
        attack = 12;
        defense = 8;
        bounds = 3;
        moves_left = 3;
        available = true;
        friendly = true;
        accuracy = 90;
        avoid = 25;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "JamesEarlJones";
    }
}