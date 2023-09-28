package greymerk.roguelike.dungeon.layout.classic;

import greymerk.roguelike.dungeon.layout.DungeonTunnel;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class Edge {

  private final Coord start;
  private final Coord end;
  private final Direction dir;
  private boolean isDone = false;
  private int extensionsRemaining = 2;

  public Edge(Coord start, Direction dir) {
    this.start = start.copy();
    this.end = start.copy();
    this.dir = dir;
  }

  public DungeonTunnel asDungeonTunnel() {
    return new DungeonTunnel(getStart().copy(), getEnd().copy());
  }

  public Coord getStart() {
    return start;
  }

  public Coord getEnd() {
    return end;
  }

  public Direction getDir() {
    return dir;
  }

  public boolean isDone() {
    return isDone;
  }

  public int getExtensionsRemaining() {
    return extensionsRemaining;
  }

  public void setDone(boolean done) {
    isDone = done;
  }

  public void setExtensionsRemaining(int extensionsRemaining) {
    this.extensionsRemaining = extensionsRemaining;
  }
}
