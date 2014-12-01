package proj4;

import org.newdawn.slick.SlickException;

public class MarySue extends Sprite
{
    MarySue() throws SlickException
    {
	super();

        // Set Attributes
	health = 153;
	attack = 16;
	defense = 10;
	bounds = 4;
	moves_left = 4;
	available = true;
	friendly = true;
	accuracy = 100;
        avoid = 40;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }
    
    public String getType()
    {
        return "MarySue";
    }
}