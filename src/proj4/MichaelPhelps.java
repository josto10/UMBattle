package proj4;

import org.newdawn.slick.SlickException;

public class MichaelPhelps extends Sprite
{
    MichaelPhelps() throws SlickException
    {
        super();
      
        // Set Attributes
        health = 21;
        attack = 12;
        defense = 5;
        bounds = 4;
        moves_left = 4;
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
        return "MichaelPhelps";
    }
}