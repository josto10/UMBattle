package proj4;
 
import java.util.*;
import org.newdawn.slick.SlickException;

public class EnemyTurn
{
    private Sprite [] targetList;
    private LevelMap map;
    public Game state;

    
    public EnemyTurn(Sprite [] enemyList, Sprite [] friendlyList, LevelMap inMap, long delta, Game inState) throws SlickException
    {
        map = inMap;
        state = inState;
        runTurn(enemyList, friendlyList, map, delta);
    }
    
    private boolean moveToward(float MoveToX, float MoveToY, long delta, Sprite mover) throws SlickException
    {
     
        while ((mover.getMovesLeft() != 0) && (isWithinOne(mover, MoveToX, MoveToY) == false))
        {
          
            if ((MoveToX > mover.getPosX())  && (map.isOccupied(mover.getPosX() + 32, mover.getPosY(), targetList) == null)
                    && (!map.isBlocked(mover.getPosX() + 32, mover.getPosY())))
            {
             // System.out.println("Moving East");
                // update image
                mover.setX(mover.getPosX() + 32);
                mover.update(delta);
                mover.setMovesLeft(mover.getMovesLeft() - 1);
                
                continue;
            }
            
            if ((MoveToX < mover.getPosX())  && (map.isOccupied(mover.getPosX() - 32, mover.getPosY(), targetList) == null)
                    && (!map.isBlocked(mover.getPosX() - 32, mover.getPosY())))
            {
             // System.out.println("Moving East");
                // update image
                 mover.setX(mover.getPosX() - 32);
                 mover.update(delta);
                mover.setMovesLeft(mover.getMovesLeft() - 1);
                continue;
            }

            if ((MoveToY > mover.getPosY())  && (map.isOccupied(mover.getPosX(), mover.getPosY() + 32, targetList) == null)
                    && (!map.isBlocked(mover.getPosX(), mover.getPosY() + 32)))
            {
             //System.out.println("Moving East");
                // update image
                 mover.setY(mover.getPosY() + 32);
                 mover.update(delta);
                mover.setMovesLeft(mover.getMovesLeft() - 1);
                continue;
            }

            if ((MoveToY < mover.getPosY())  && (map.isOccupied(mover.getPosX(), mover.getPosY() - 32, targetList) == null)
                    && (!map.isBlocked(mover.getPosX(), mover.getPosY() - 32)))
            {
              //System.out.println("Moving East");
                // update image
                mover.setY(mover.getPosY() - 32);
                mover.update(delta);
                mover.setMovesLeft(mover.getMovesLeft() - 1);
                continue;
            }

            //Default move somewhere if possible
            break;
        }

        if (isWithinOne(mover, MoveToX, MoveToY))
        {
            return true;
        }

        return false;
    }

    private void runTurn(Sprite [] enemyList, Sprite [] friendlyList, LevelMap map, long delta) throws SlickException
    {
        targetList = friendlyList.clone();
       
        for (final Sprite enemy : enemyList)
        {
            if (enemy != null)
            {
                // Sort to order list of targets
                Arrays.sort(targetList, new Comparator<Sprite>() 
                {
                    public int compare(Sprite spriteOne, Sprite spriteTwo) 
                    {
                        if (spriteOne == null) return 0;
                        if (spriteTwo == null) return 0;
                        double distanceOne = getDistanceBetween(enemy, spriteOne);
                        double distanceTwo = getDistanceBetween(enemy, spriteTwo);
                        if (distanceOne == distanceTwo)
                        {
                          return 0;
                        }
                        else if (distanceOne > distanceTwo)
                        {
                          return 1;
                        }
                        else 
                        {
                          return -1;
                        }
                    }
                });
                
                Sprite target = null;

                for (Sprite friendly : targetList)
                {
                    if (friendly != null)
                    {
                        //System.out.println(String.format("Acquired target at (%f, %f)", friendly.getPosX() , friendly.getPosY()));
                        target = friendly;
                        break;
                    }
                }

               if (target != null)
               {
                 //System.out.println(String.format("My X: %f, My Y: %f", enemy.getPosX(), enemy.getPosY()));
                 //System.out.println(String.format("Target X: %f, Target Y: %f", target.getPosX(), target.getPosY()));
                 boolean canAttack = moveToward(target.getPosX(), target.getPosY(), delta, enemy);

                 if (canAttack)
                 {
                    attackFriendly(enemy, target);
                 }
               }
            }
        }
    }

    private double getDistanceBetween(Sprite spriteOne, Sprite spriteTwo)
    {
        double tempX = Math.abs(spriteOne.getPosX() - spriteTwo.getPosX());
        double xDiff = tempX * tempX; 
        double tempY = Math.abs(spriteOne.getPosY() - spriteTwo.getPosY());
        double yDiff = tempY * tempY; 
        return Math.sqrt(xDiff + yDiff);
    }

    private boolean isWithinOne(Sprite attacker, float X, float Y) throws SlickException
    {
        Sprite temp = new MarySue();
        temp.setX(X);
        temp.setY(Y);
       
        return (getDistanceBetween(attacker, temp) <= 32.0f);
    }

    private void attackFriendly(Sprite attacker, Sprite defender)
    {
        BattleClass battle = new BattleClass(attacker, defender);
        System.out.println(battle.toString());
        
        
        if (attacker.getHealth() <= 0)
        {
            map.setEnemyList(state.removeElements(map.getEnemyList(), attacker));
        }  
        else if (defender.getHealth() <= 0)
        {
           state.setFriendlyList(state.removeElements(state.getFriendlyList(), defender));
        }
    }
}
