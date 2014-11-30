package proj4;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import proj4.*;

public class MarySue extends Sprite
{

  MarySue() throws SlickException
  {
    super();
    Image[] standing =
    {
      new Image("data/MarySueIdle1.png"), new Image("data/MarySueIdle2.png")
    };
    Image[] tired =
    {
      new Image("data/MarySueMoved1.png"), new Image("data/MarySueMoved2.png")
    };
    Image[] grabbed =
    {
      new Image("data/MarySueSelected1.png"), new Image("data/MarySueSelected2.png")
    };
    Image[] translate =
    {
      new Image("data/MarySueMoving1.png"), new Image("data/MarySueMoving2.png")
    };

    int[] StandingDuration =
    {
      800, 800
    };

    idle = new Animation(standing, StandingDuration, true);
    moved = new Animation(tired, StandingDuration, true);
    selected = new Animation(grabbed, StandingDuration, true);
    moving = new Animation(translate, StandingDuration, false);

    sprite = idle;

    // set initial location
    //    x = 36;
    //    y = 36;
    // set specific values
    health = 100;
    attack = 100;
    defense = 10;
    bounds = 100;
    moves_left = 100;
    available = true;
    friendly = true;
    accuracy = 100;
    avoid = 3;
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
