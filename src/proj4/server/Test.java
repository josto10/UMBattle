package proj4.server;

import static java.lang.System.out;

public class Test
{
  public static void main(String[] args)
  {
    Database db = new Database();
    out.println(db.getPlayer("asdf"));
  }
}