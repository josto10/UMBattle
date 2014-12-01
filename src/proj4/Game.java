package proj4;

import java.awt.List;
import java.util.LinkedList;
import java.util.Scanner;
import proj4.*;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import proj4.server.GameClient;

public class Game extends BasicGameState
{
  public static final int TOTAL_NUM_LEVELS = 2;
  private int currentLevel;
  private float MoveToX = 32;
  private float MoveToY = 32;
  private TextField characterData;
  private TextField enemyData;
  private static TextField battleOutput;
  private Sprite[] friendlyList;
  private Sprite wizard;
  private Sprite warlock;
  private boolean attackEngaged;
  private static boolean battleOutputShowing;
  public static boolean isNewGame;
  private Image selectedMenu;
  private Image deselectedMenu;
  private Image attackConfirmation;
  private static boolean tryingToAttack;
  private Location confirmationLocation;
  public static String user = "tempPlayer";
  public static GameClient client = new GameClient("127.0.0.1", 45000);
  LevelMap map;
  private StateBasedGame mainGameReference;
  private float x = 32f, y = 32f;

  private static boolean[][] blocked;
  private static final int SIZE = 32;
  
  private Image openingText;
  private boolean isOpeningTextShowing;
    
  public Game(int ID)
  {
    super();
    client.startClient();
  }

   public void init(GameContainer container, StateBasedGame game) throws SlickException
   {
     //container.setVSync(true);
     container.setShowFPS(false);
     container.setTargetFrameRate(60);
     container.setSmoothDeltas(true);
     characterData = new TextField(container, container.getDefaultFont(), 1056, 608, 128, 192);
     characterData.setTextColor(new Color(255, 255, 255));
     characterData.setBackgroundColor(new Color(0,0,0,0.75f));

     enemyData = new TextField(container, container.getDefaultFont(), 1056, 608, 128, 192);
     enemyData.setTextColor(new Color(255, 255, 255));
     enemyData.setBackgroundColor(new Color(0,0,0,0.75f));

     battleOutput = new TextField(container, container.getDefaultFont(), 0, 0, 352, 160);
     battleOutput.deactivate();
     battleOutput.setConsumeEvents(false);
     battleOutput.setFocus(false);
     battleOutput.setCursorVisible(false);
     battleOutput.setTextColor(new Color(255, 255, 255));
     battleOutput.setBackgroundColor(new Color(0,0,0,0.75f));
        
    characterData.deactivate();
    characterData.setConsumeEvents(false);
    characterData.setFocus(false);
    characterData.setCursorVisible(false);

    enemyData.deactivate();
    enemyData.setConsumeEvents(false);
    enemyData.setFocus(false);
    enemyData.setCursorVisible(false);
    
    selectedMenu = new Image("data/SelectedMenu.png");
    deselectedMenu = new Image("data/DeselectedMenu.png");
    attackConfirmation = new Image("data/attackConfirmation.png");
    battleOutputShowing = false;
   }

   public void initNewLevel(int levelNum, String userName) throws SlickException
   {
    user = userName;
     
    if (!client.doesPlayerExist(userName))
    {
        client.addPlayer(userName);
    }
    else if (isNewGame)
    {
        client.resetPlayer(user);
        
        openingText = new Image("data/WINNER.png");
        isOpeningTextShowing = true;
    }
     
     // Get the map
     currentLevel = loadFriendlies();
     map = new LevelMap();
     map.init(levelNum, (!isNewGame));
  
     wizard = null;
   }

  public void enter(GameContainer container, StateBasedGame game)
  {
    mainGameReference = game;
  }

  public int getID()
  {
    return UMBattle.GAME; // game class
  }

  public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
  {   
    boolean testPortal = false;
    if (wizard != null)
    {
     testPortal = wizard.moveToward(MoveToX, MoveToY, delta);
     wizard.update(delta);
     if (testPortal) advanceLevel(game);
    }
    
    for (Sprite enemy : map.getEnemyList())
    {
      if (enemy != null)
      {
        enemy.update(delta);
      }
    }
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

    if (warlock != null)
    {
     populateCharacterData();
     populateEnemyData();
     
     Location newOrigin = centerConfirmationIfOffScreen(warlock.getPosX() + 32, warlock.getPosY());
     attackConfirmation.draw(newOrigin.x, newOrigin.y);
     characterData.setLocation((int)newOrigin.x, (int)(newOrigin.y + 96));
     enemyData.setLocation((int)(newOrigin.x + 128), (int)(newOrigin.y + 96));
     
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
    
    if (isOpeningTextShowing)
    {
        openingText.draw(0, 0, 1184, 800);
    }
  }

  @Override
  public void mousePressed(int button, int posX, int posY)
  { 
    if (isOpeningTextShowing)
    {
       isOpeningTextShowing = false;
    }
      
      
      
    if (battleOutputShowing)
    {
      battleOutputShowing = false;
      if (tryingToAttack)
      {
        try
        {
          EnemyTurn revengeOfTheSith = new EnemyTurn (map.getEnemyList(), friendlyList, map, 0, this);
        }
        catch (SlickException e)
        {
          e.printStackTrace();
        }
      }
      return;
    }

    if (wizard != null)
    {
      if (warlock == null) clickedSelected(posX, posY);
      else
      {
        // check for mouse position in popped menu
        if (posX > confirmationLocation.x + 32 && posX < confirmationLocation.x + 132 + 32
           && posY > confirmationLocation.y + 32 && posY < confirmationLocation.y + 96)
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
          try 
          {
            if (isWithinOne(wizard, character))
            {
                warlock = character;
                return;
            }
           }
          catch (SlickException e)
            {
              e.printStackTrace();
            }
        }
      }
    }

  private void clickedNotSelected(int posX, int posY)
  {
    // End Turn menu option selected
    if (posX > 1056 && posY < 192 && posY > 128)
      {
          mainGameReference.enterState(0);
      }
      else if (posX > 1056 && posY < 128 && posY > 64)
      {
          saveGame();
      }
      else if (posX > 1056 && posY < 64 && posY > 0)
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




  public void keyPressed(int key, char c)
  {
    if (wizard == null)  return;
    if (wizard.getPosX() != MoveToX || wizard.getPosY() != MoveToY) return;

    if(key == Input.KEY_UP)
    {
      if(wizard.getMovesLeft() == 0) return;
      MoveToY = wizard.getPosY() - 32;
    }
    else if(key == Input.KEY_DOWN)
    {
      if(wizard.getMovesLeft() == 0) return;
      MoveToY = wizard.getPosY() + 32;
    }
    else if(key == Input.KEY_RIGHT)
    {
      if(wizard.getMovesLeft() == 0) return;
      MoveToX = wizard.getPosX() + 32;
    }
    else if(key == Input.KEY_LEFT)
    {
      if(wizard.getMovesLeft() == 0) return;
      MoveToX = wizard.getPosX() - 32;
    }

    //wizard.pushMoves(new Location(MoveToX, MoveToY));

    // So it doesnt get stuck
    if (map.isBlocked(MoveToX, MoveToY) || map.isOccupied(MoveToX, MoveToY, friendlyList) != null)
    {
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
        enemy.canAttack = true;
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

  public static void helpEnemiesFight(float inX, float inY, String inResults)
  {
    
    centerBattleIfOffScreen(inX + 32, inY);
    battleOutput.setText(inResults);
    battleOutputShowing = true;
  }

  public static void setTryingToAttack(boolean inBool)
  {
    tryingToAttack = inBool;
  }

  private void executeBattle(Sprite litigator, Sprite defendant) throws SlickException
  {
    if (!isWithinOne(litigator, defendant))
    {
        return;
    }
    attackEngaged = false;
    centerBattleIfOffScreen(warlock.getPosX() + 32, warlock.getPosY());
    BattleClass battle = new BattleClass(litigator, defendant);

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
  
   public Location centerConfirmationIfOffScreen(float inX, float inY)
  {
    if (inX > 800)
    {
      inX = 450;
    }

    if (inY > 500)
    {
      inY = 300;
    }
    
    confirmationLocation = new Location(inX, inY);
    return confirmationLocation;
  }
  

  public static void centerBattleIfOffScreen(float inX, float inY)
  {
    if (inX > 800)
    {
      inX = 450;
    }

    if (inY > 500)
    {
      inY = 300;
    }

    battleOutput.setLocation((int)inX, (int)inY);
  }

  public String toString()
  {
    String outString = "";
    outString += currentLevel + " ";
    for (int i = 0; i < friendlyList.length; ++i)
    {
      if (friendlyList[i] != null)
      {
        outString += friendlyList[i].toString() + " ";
      }
    }
    for (int i = 0; i < map.getEnemyList().length; ++i)
    {
      if (map.getEnemyList()[i] != null)
      {
        outString += map.getEnemyList()[i].toString() + " ";
      }
    }

    return outString;
  }

  private boolean isWithinOne(Sprite attacker, Sprite defender) throws SlickException
  {
    return (getDistanceBetween(attacker, defender) <= 32.0f);
  }

   private double getDistanceBetween(Sprite spriteOne, Sprite spriteTwo)
  {
    double tempX = Math.abs(spriteOne.getPosX() - spriteTwo.getPosX());
    double xDiff = tempX * tempX; 
    double tempY = Math.abs(spriteOne.getPosY() - spriteTwo.getPosY());
    double yDiff = tempY * tempY; 
    return Math.sqrt(xDiff + yDiff);
  }

  private void saveGame()
  {
      System.out.println(toString());
      
      try
      {
          System.out.println(client.savePlayer(user, toString()));
      }
      catch (NullPointerException e)
      {
          System.out.println("Please start the server before trying to save!");
      }
    
  }

  private void loadFriendlies() throws SlickException
  {
    String friendlyString = client.getFriendlyString(user);
    System.out.println(friendlyString);
    Scanner sc = new Scanner(friendlyString);
    int numFriendlies = sc.nextInt();
    friendlyList = new Sprite[numFriendlies];
    for (int i = 0; i < numFriendlies; ++i)
    {
      switch (sc.next())
      {
        case "MarySue":
          friendlyList[i] = new MarySue();
          break;
        case "You":
          friendlyList[i] = new You();
          break;
        case "DenardRobinson":
          friendlyList[i] = new DenardRobinson();
          break;
        case "BradyHoke":
          friendlyList[i] = new BradyHoke();
          break;
        case "TomBrady":
          friendlyList[i] = new TomBrady();
          break;
        case "MarkSchlissel":
          friendlyList[i] = new MarkSchlissel();
          break;
        case "JamesEarlJones":
          friendlyList[i] = new JamesEarlJones();
          break;
        case "GeraldFord":
          friendlyList[i] = new GeraldFord();
          break;
        case "LarryPage":
          friendlyList[i] = new LarryPage();
          break;
        case "MichaelPhelps":
          friendlyList[i] = new MichaelPhelps();
          break;
        case "StephenMRoss":
          friendlyList[i] = new StephenMRoss();
          break;
      }
      friendlyList[i].setAvailable(sc.nextBoolean());
      sc.next();
      friendlyList[i].setSelected(sc.nextBoolean());
      friendlyList[i].setX(sc.nextInt());
      friendlyList[i].setY(sc.nextInt());
      friendlyList[i].initHealth(sc.nextInt());
      sc.next();
      sc.next();
      sc.next();
      friendlyList[i].setMovesLeft(sc.nextInt());
      sc.next();
      sc.next();
    }
    sc.close();
  }
}