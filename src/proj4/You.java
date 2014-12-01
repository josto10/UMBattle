package proj4;

import org.newdawn.slick.SlickException;

public class You extends Sprite
{
    You() throws SlickException
    {
        super();
     
        // Set Attributes
        health = 24;
        attack = 10;
        defense = 5;
        bounds = 4;
        moves_left = 4;
        available = true;
        friendly = true;
        accuracy = 100;
        avoid = 28;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

    public String getType()
    {
        return "You";
    }
    
    public void levelUp()
    {
      health += 3;
      attack += 1;
      defense += 1;
      accuracy += 5;
      avoid += 5;
    }
}