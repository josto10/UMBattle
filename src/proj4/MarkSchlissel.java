package proj4;

import org.newdawn.slick.SlickException;

public class MarkSchlissel extends Sprite
{
    MarkSchlissel() throws SlickException
    {
        super();
    
        // Set Attributes
        health = 25;
        attack = 10;
        defense = 4;
        bounds = 3;
        moves_left = 3;
        available = true;
        friendly = true;
        accuracy = 75;
        avoid = 12;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "MarkSchlissel";
    }
}