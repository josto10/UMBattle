package proj4;

import java.util.Random;
import proj4.*; 


public class BattleClass
{
  private int plaintiffHitRate;
  private int defendantHitRate;
  Random randomGenerator = new Random();
  private Sprite attacker;
  private Sprite defender;
  private String damageDealt;
  private String counterDealt;
  private String hitString;
  private String counterString;

  BattleClass(Sprite plaintiff, Sprite defendant)
  {
    attacker = plaintiff;
    defender = defendant;
    int difference = plaintiff.getAccuracy() - defendant.getAvoid();
    if (difference <= 0)
    {
      plaintiffHitRate = 0;
    }
    else
    {
      plaintiffHitRate = difference;
    }

    int counterDifference = defendant.getAccuracy() - plaintiff.getAvoid();
    if (counterDifference <= 0)
    {
      defendantHitRate = 0;
    }
    else
    {
      defendantHitRate = difference;
    }

    // DRAW BATTLE

    runBattle(plaintiff, defendant);

    // EXIT BATTLE
  }


  private void runBattle(Sprite plaintiff, Sprite defendant)
  {
    boolean didAttack = Attack(plaintiff, defendant);

    if (didAttack)
    {
      // HIT
     hitString = "hit";

    }
    else
    {
      // MISSED
      hitString = "missed";
    }

    if (defendant.getHealth() <= 0) return;
    if (plaintiff.getHealth() <= 0) return;

    boolean didCounter = Counter(plaintiff, defendant);

    if (didCounter)
    {
      // HIT Counter
      counterString = "countered";
    }
    else
    {
      // MISSED Counter
      counterString = "missed";
    }
  }


  private boolean Attack(Sprite plaintiff, Sprite defendant)
  {
    int randomInt = randomGenerator.nextInt(100);

    int potentialDamage = plaintiff.getAttack() - defendant.getDefense();
    if (potentialDamage < 0)
    {
      potentialDamage = 0;
    }

    if (plaintiffHitRate > randomInt)
    {
      defendant.setHealth(potentialDamage);
      damageDealt = String.format("%d", potentialDamage);
      return true;
    }
    else
    {
      damageDealt = "0";
    }

    return false;
  }

  private boolean Counter(Sprite plaintiff, Sprite defendant)
  {
    int randomInt = randomGenerator.nextInt(100);

    int potentialDamage = defendant.getAttack() - plaintiff.getDefense();
    if (potentialDamage < 0)
    {
      potentialDamage = 0;
    }

    if (defendantHitRate > randomInt)
    {
      plaintiff.setHealth(potentialDamage);
      counterDealt = String.format("%d", potentialDamage);
      return true;
    }
    else
    {
      counterDealt = "0";
    }

    return false;
  }

  public String toString(Sprite plaintiff, Sprite defendant)
  {
    String result = "-------- BATTLE -------- \n";
    result = result + plaintiff.getType() + " attacked " + defendant.getType() + "\n";
    result = result + plaintiff.getType() + " " + hitString + " dealing " + damageDealt + " damage\n";
    if (defendant.getHealth() <= 0)
    {
      result = result + defendant.getType() + " could not counter\n"; 
    }
    else
    {
      result = result + defendant.getType() + " " + counterString + " inflicting " + counterDealt + " damage\n";
    }
    result = result + plaintiff.getType() + "'s health after battle: " + String.format("%d\n", plaintiff.getHealth());
    result = result + defendant.getType() + "'s health after battle: " + String.format("%d\n", defendant.getHealth());
    if (plaintiff.getHealth() <= 0)
    {
      result = result + plaintiff.getType() + " died...\n";
    }
    else if (defendant.getHealth() <= 0)
    {
      result = result + defendant.getType() + " died...\n";
    }
    return result;
  }

}