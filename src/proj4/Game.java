package proj4;

import java.awt.List;
import java.util.LinkedList;
import proj4.*;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGameState
{
  public static final int TOTAL_NUM_LEVELS = 2;
  private int currentLevel;
  private float MoveToX = 32;
  private float MoveToY = 32;
  private TextField characterData;
  private TextField enemyData;
  private TextField battleOutput;
  private Sprite[] friendlyList;
  private Sprite wizard;
  private Sprite warlock;
  private boolean attackEngaged;
  private boolean battleOutputShowing;
  private Image selectedMenu;
  private Image deselectedMenu;
  private Image attackConfirmation;
  LevelMap map;
  private float x = 32f, y = 32f;

  private static boolean[][] blocked;
  private static final int SIZE = 32;
    
    public Game(int ID)
    {
        super();
    }
    
     public void init(GameContainer container, StateBasedGame game) throws SlickException
     {
        characterData = new TextField(container, container.getDefaultFont(), 1056, 608, 128, 192);
        characterData.setTextColor(new Color(255, 255, 255));
        characterData.setBackgroundColor(new Color(0,0,0,0.75f));
        
        enemyData = new TextField(container, container.getDefaultFont(), 1056, 608, 128, 192);
        enemyData.setTextColor(new Color(255, 255, 255));
        enemyData.setBackgroundColor(new Color(0,0,0,0.75f));
        
        battleOutput = new TextField(container, container.getDefaultFont(), 0, 0, 256, 288);
        battleOutput.setTextColor(new Color(255, 255, 255));
        battleOutput.setBackgroundColor(new Color(0,0,0,0.75f));
     }
     
     public void initNewLevel(int levelNum) throws SlickException
     {
       // Get the map
       map = new LevelMap();
       currentLevel = levelNum;
       map.init(levelNum);
       
       // initialize the default values ** maybe go in the normal init?? **
      selectedMenu = new Image("data/SelectedMenu.png");
      deselectedMenu = new Image("data/DeselectedMenu.png");
      attackConfirmation = new Image("data/attackConfirmation.png");
      battleOutputShowing = false;
       
       // Get friendly list size from player data when we instantiate
       friendlyList = new Sprite[3];
       friendlyList[0] = new MarySue();
       friendlyList[1] = new MarySue();
       friendlyList[2] = new MarySue();
       friendlyList[0].setX(32);
       friendlyList[0].setY(32);
       friendlyList[1].setX(160);
       friendlyList[1].setY(160);
       friendlyList[2].setX(320);
       friendlyList[2].setY(32);
       
       // no selection
       wizard = null;
     }
    
    public void enter(GameContainer container, StateBasedGame game)
    {
      // Empty
    }
    
    public int getID()
    {
        return UMBattle.GAME; // game class
    }
    
     public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
     {
         map.render();
         for (Sprite friendly : friendlyList)
         {
           if (friendly != null)
           {
             friendly.draw();
           } 
         }
         
         for (Sprite enemy : map.getEnemyList())
         {
           if (enemy != null)
           {
             enemy.draw();
           }
         }
         
         // fix poppup when it would write off the screen!!! *****
         if (warlock != null)
         {
          attackConfirmation.draw(warlock.getPosX()+32, warlock.getPosY());
          populateCharacterData();
          populateEnemyData();
          characterData.setLocation((int)warlock.getPosX()+32, (int)warlock.getPosY()+96);
          enemyData.setLocation((int)warlock.getPosX()+128+32, (int)warlock.getPosY()+96);
          enemyData.render(container, g);
          characterData.render(container, g);
         }
         
         if (battleOutputShowing)
         {
           battleOutput.render(container, g);
         }
         
         // 608 for lower right
         if (wizard != null && warlock == null)
         {
           populateCharacterData();
           characterData.setLocation(1056, 608);
           characterData.render(container, g);
           selectedMenu.draw(1056, 0);
         }
         else 
         {
           deselectedMenu.draw(1056, 0);
         }
         
     }

    @Override
    public void mousePressed(int button, int posX, int posY)
    { 
      if (battleOutputShowing)
      {
        battleOutputShowing = false;
        return;
      }
      
      if (wizard != null)
      {
        if (warlock == null) clickedSelected(posX, posY);
        else
        {
          // check for mouse position in popped menu
          if (posX > warlock.getPosX() + 32 && posX < warlock.getPosX() + 132 + 32
             && posY > warlock.getPosY() + 32 && posY < warlock.getPosY() + 96)
          {
            try
            {
              executeBattle(wizard, warlock);
              warlock = null;
              endSelected();
            }
            catch (SlickException e)
            {
              e.printStackTrace();
            }
            return;
          }
          else if (posX > warlock.getPosX() + 132+ 32 && posX < warlock.getPosX() + 254 + 32
             && posY > warlock.getPosY() + 32 && posY < warlock.getPosY() + 96)
          {
            warlock = null;
          }
        }
      }
      else
      {
        clickedNotSelected(posX, posY);
      }
    }
    
    private void clickedSelected(int posX, int posY)
    {
        if (posX > 1056 && posY < 192 && posY > 128)
        {
          endSelected();
        }
        else if (posX > 1056 && posY < 128 && posY > 64)
        {
          cancelSelected();
        }
        else if (posX > 1056 && posY < 64 && posY > 0)
        {
          attackEngaged = true;
        }
        else
        {
          checkAttack(posX, posY);
        }
    }
    
    private void endSelected()
    {
      wizard.setSelected(false);
      wizard.setAvailable(false);
      wizard.update(0);
      wizard = null;
    }
    
    private void cancelSelected()
    {
      wizard.setSelected(false);
      while (wizard.getSizeMoves() > 1)
      {
          wizard.popMoves();
      }

      if (wizard.getSizeMoves() > 0)
      {
          wizard.setX(wizard.peekMoves().x);
          wizard.setY(wizard.peekMoves().y);
          wizard.popMoves();
          wizard.update(0);
          MoveToX = wizard.getPosX();
          MoveToY = wizard.getPosY();
          wizard.setMovesLeft(wizard.getBounds());
          wizard.setSelected(true);
      }
    }
    
    private void checkAttack(int posX, int posY)
    {
      if (!attackEngaged) return;
      posX = (posX / 32) * 32;
      posY = (posY / 32) * 32;
      for (Sprite character : map.getEnemyList())
      {
        if (character == null) continue;
        if (posX == character.getPosX() && posY == character.getPosY())
        {
          warlock = character;
          return;
        }
      }
    }
    
    private void clickedNotSelected(int posX, int posY)
    {
      // End Turn menu option selected
      if (posX > 1056 && posY < 192)
      {
        endPlayerTurn();
        return;
      }
      
      posX = (posX / 32) * 32;
      posY = (posY / 32) * 32;
      for (Sprite character : friendlyList)
      {
        if(!character.isAvailable()) continue;
        if (posX == character.getPosX() && posY == character.getPosY())
        {
          wizard = character;
          MoveToX = wizard.getPosX();
          MoveToY = wizard.getPosY();
          // PUSH INITIAL POSITION ON MOVES STACK
          wizard.pushMoves(new Location(wizard.getPosX(), wizard.getPosY()));
          wizard.setSelected(true);
          return;
        }
      }
    }
    

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {
      Input input = container.getInput();
            
        // check for change for move counter
      boolean testPortal = false;
      if (wizard != null)
      {
        testPortal = wizard.moveToward(MoveToX, MoveToY, delta);
        wizard.update(delta);
        if (testPortal) advanceLevel(game);
      }
    }

    public void keyPressed(int key, char c)
    {
      if (wizard == null) return;
      
      if (wizard.getPosX() != MoveToX || wizard.getPosY() != MoveToY) return;
      
      if(wizard.getMovesLeft() == 0) return;

      if(key == Input.KEY_UP)
      {
        MoveToY = wizard.getPosY() - 32;
      }
      else if(key == Input.KEY_DOWN)
      {
        MoveToY = wizard.getPosY() + 32;
      }
      else if(key == Input.KEY_RIGHT)
      {
        MoveToX = wizard.getPosX() + 32;
      }
      else if(key == Input.KEY_LEFT)
      {
        MoveToX = wizard.getPosX() - 32;
      }
      else
      {
        // other key pressed
        return;
      }
      
      //wizard.pushMoves(new Location(MoveToX, MoveToY));

      // So it doesnt get stuck
      if (map.isBlocked(MoveToX, MoveToY) || map.isOccupied(MoveToX, MoveToY, friendlyList) != null)
      {
        System.out.println("Im here");
        MoveToX = wizard.getPosX();
        MoveToY = wizard.getPosY();
      }
    }
    
    public Sprite[] removeElements(Sprite[] input, Sprite deleteMe) 
    {
      LinkedList<Sprite> result = new LinkedList<>();

      for(Sprite item : input)
      {
          if(!deleteMe.equals(item))
          {
             result.add(item); 
          }  
      }
      
      return result.toArray(input);
    }
    
   
    public void advanceLevel(StateBasedGame game) throws SlickException
    {
      if (currentLevel == TOTAL_NUM_LEVELS)
      {
        game.enterState(2);
      }
      else
      {
        ((UMBattle)game).setLevel(currentLevel + 1);
        game.enterState(3);
      }
    }
    
    private void endPlayerTurn()
    {
      wizard = null;
      for (Sprite character : friendlyList)
      {
        character.setMovesLeft(character.getBounds());
        character.setSelected(false);
        character.setAvailable(true);
        character.update(0);
        character.resetMoves();
      }
      for (Sprite enemy : map.getEnemyList())
      {
        if (enemy != null)
        {
          enemy.setMovesLeft(enemy.getBounds());
        }
      }
      
      try
      {
        EnemyTurn revengeOfTheSith = new EnemyTurn (map.getEnemyList(), friendlyList, map, 0, this);
      }
      catch (SlickException e)
      {
        e.printStackTrace();
      }
    }

    public String toString()
    {
      String outString = "";
      outString += currentLevel + " ";
      for (Sprite character : friendlyList)
      {
        outString += character.toString() + " ";
      }
      
      for (Sprite enemy : map.getEnemyList())
      {
        outString += enemy.toString() + " ";
      }
      
      return outString;
    }
    
    
     private double getDistanceBetween(Sprite spriteOne, Sprite spriteTwo)
    {
        double tempX = Math.abs(spriteOne.getPosX() - spriteTwo.getPosX());
        double xDiff = tempX * tempX; 
        double tempY = Math.abs(spriteOne.getPosY() - spriteTwo.getPosY());
        double yDiff = tempY * tempY; 
        return Math.sqrt(xDiff + yDiff);
    }

    private boolean isWithinOne(Sprite attacker, Sprite defender) throws SlickException
    {
        return (getDistanceBetween(attacker, defender) <= 32.0f);
    }
    //256 * 288
    private void executeBattle(Sprite litigator, Sprite defendant) throws SlickException
    {
        if (!isWithinOne(litigator, defendant))
        {
            return;
        }
        attackEngaged = false;
        
        battleOutput.setLocation((int)warlock.getPosX() + 32, (int)warlock.getPosY());
        
        BattleClass battle = new BattleClass(litigator, defendant);
        
        System.out.println(battle.toString(litigator, defendant));
              
        
        // we move stuff
        
        
        battleOutput.setText(battle.toString(litigator, defendant));
        battleOutputShowing = true;
        
        if (defendant.getHealth() <= 0)
        {
            map.setEnemyList(removeElements(map.getEnemyList(), defendant));
        }  
        else if (litigator.getHealth() <= 0)
        {
           setFriendlyList(removeElements(friendlyList, litigator));
        }
    }
    
    public Sprite[] getFriendlyList()
    {
        return friendlyList;
    }
    
    public void setFriendlyList(Sprite[] input)
    {
        friendlyList = input;
    }
    
    private void populateCharacterData()
    {
      String displayString = "";
      displayString += wizard.getType() + '\n';
      displayString += "Health: " + wizard.getHealth() + '\n';
      displayString += "Moves: " + wizard.getMovesLeft() + '\n';
      displayString += "Attack: " + wizard.getAttack() + '\n';
      displayString += "Defense: " + wizard.getDefense() + '\n';
      displayString += "Agility: " + wizard.getAvoid() + '\n';
      displayString += "Accuracy: " + wizard.getAccuracy() + '\n';
      characterData.setText(displayString);
    }
    
    private void populateEnemyData()
    {
      String displayString = "";
      displayString += warlock.getType() + '\n';
      displayString += "Health: " + warlock.getHealth() + '\n';
      displayString += "Moves: " + warlock.getMovesLeft() + '\n';
      displayString += "Attack: " + warlock.getAttack() + '\n';
      displayString += "Defense: " + warlock.getDefense() + '\n';
      displayString += "Agility: " + warlock.getAvoid() + '\n';
      displayString += "Accuracy: " + warlock.getAccuracy() + '\n';
      enemyData.setText(displayString);
    }
}
