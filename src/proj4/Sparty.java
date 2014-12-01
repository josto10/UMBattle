package proj4;

import org.newdawn.slick.SlickException;

public class Sparty extends Sprite
{
    Sparty() throws SlickException
    {
        super();
	
        // Set Attributes
        health = 24;
        attack = 10;
        defense = 7;
        bounds = 4;
        moves_left = 4;
        available = true;
        friendly = false;
        accuracy = 100;
        avoid = 15;
    }
    
    public void draw()
    {
        sprite.draw(x, y);
    }
    
    public String getType()
    {
        return "Sparty";
    }
}