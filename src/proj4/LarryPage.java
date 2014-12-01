package proj4;

import org.newdawn.slick.SlickException;

public class LarryPage extends Sprite
{
    LarryPage() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 14;
        attack = 18;
        defense = 0;
        bounds = 4;
        moves_left = 4;
        available = true;
        friendly = true;
        accuracy = 105;
        avoid = 50;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }
    
    public String getType()
    {
        return "LarryPage";
    }
}