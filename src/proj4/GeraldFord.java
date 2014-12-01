package proj4;

import org.newdawn.slick.SlickException;

public class GeraldFord extends Sprite
{
    GeraldFord() throws SlickException
    {
        super();
   
        // Set Attributes
        health = 35;
        attack = 8;
        defense = 10;
        bounds = 3;
        moves_left = 3;
        available = true;
        friendly = true;
        accuracy = 80;
        avoid = 20;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

   public String getType()
    {
        return "GeraldFord";
    }
}