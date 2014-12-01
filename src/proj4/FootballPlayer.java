package proj4;

import org.newdawn.slick.SlickException;

public class FootballPlayer extends Sprite
{
    FootballPlayer() throws SlickException
    {
        super();
    
        // Set Attributes
        health = 30;
        attack = 11;
        defense = 12;
        bounds = 2;
        moves_left = 2;
        available = true;
        friendly = false;
        accuracy = 60;
        avoid = 0;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "FootballPlayer";
    }
}