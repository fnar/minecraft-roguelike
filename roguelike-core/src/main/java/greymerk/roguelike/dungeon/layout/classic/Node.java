package greymerk.roguelike.dungeon.layout.classic;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class Node {

  private final Coord coord;
  private final Direction direction;

  public Node(Coord coord, Direction direction) {
    this.coord = coord;
    this.direction = direction;
  }

  public Coord getCoord() {
    return coord.copy();
  }

  public Direction getDirection() {
    return direction;
  }

}
