package proj4;

import org.newdawn.slick.SlickException;

public class TomIzzo extends Sprite
{
    TomIzzo() throws SlickException
    {
        super();
	
        // Set Attributes
        health = 41;
        attack = 13;
        defense = 5;
        bounds = 0;
        moves_left = 0;
        available = true;
        friendly = false;
        accuracy = 100;
        avoid = 30;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }
    
    public String getType()
    {
        return "TomIzzo";
    }
}