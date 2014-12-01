package proj4;

import org.newdawn.slick.SlickException;

public class BasketballPlayer extends Sprite
{
    BasketballPlayer() throws SlickException
    {
        super();
    
        // Set Attributes
        health = 23;
        attack = 13;
        defense = 0;
        bounds = 4;
        moves_left = 4;
        available = true;
        friendly = false;
        accuracy = 120;
        avoid = 30;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "BasketballPlayer";
    }
}