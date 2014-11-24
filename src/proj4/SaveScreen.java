package proj4;

  import org.lwjgl.input.Mouse;
  import org.newdawn.slick.GameContainer;
  import org.newdawn.slick.Graphics;
  import org.newdawn.slick.Image;
  import org.newdawn.slick.SlickException;
  import org.newdawn.slick.state.BasicGameState;
  import org.newdawn.slick.state.StateBasedGame;

  /**
   *
   * @author Not Joey
   */
  public class SaveScreen extends BasicGameState
  {
    Image winner;
    public int nextLevel;
      public SaveScreen(int id)
      {
          super();
      }
      
      public void init(GameContainer container, StateBasedGame game) throws SlickException
      {
        winner = new Image("data/Saver.png");
      }
      
      public void enter(GameContainer container, StateBasedGame game) throws SlickException
      {
          this.init(container, game);
      }
      
      public int getID()
      {
          return 3;
      }
      
      public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
      {
        winner.draw(0, 0, 1184, 800);
      }

      public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
      {
          int posX = Mouse.getX();
          int posY = Mouse.getY();
          
        
          if ((posX > 0 && posX < 1184) && (posY > 0 && posY < 800))
          {
              if (Mouse.isButtonDown(0))
              {
                  ((UMBattle)sbg).help(nextLevel);
                  sbg.enterState(1);
              }
          }
      }
  }

