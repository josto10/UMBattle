package proj4;

import org.newdawn.slick.SlickException;

public class Student extends Sprite
{
    Student() throws SlickException
    {
        super();
      
        // Set Attributes
        health = 18;
        attack = 8;
        defense = 4;
        bounds = 3;
        moves_left = 3;
        available = true;
        friendly = false;
        accuracy = 80;
        avoid = 10;
    }
  
    public void draw()
    {
        sprite.draw(x, y);
    }

   public String getType()
    {
        return "Student";
    }
}