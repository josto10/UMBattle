package proj4;

import org.newdawn.slick.SlickException;

public class DenardRobinson extends Sprite
{
    DenardRobinson() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 22;
        attack = 10;
        defense = 2;
        bounds = 5;
        moves_left = 5;
        available = true;
        friendly = true;
        accuracy = 50;
        avoid = 80;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "DenardRobinson";
    }
}