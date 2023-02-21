package greymerk.roguelike.dungeon.layout.classic;

import java.util.Random;

import greymerk.roguelike.dungeon.layout.DungeonTunnel;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class Edge {

  private final LayoutGeneratorClassic layoutGeneratorClassic;
  private final Coord start;
  private final Coord end;
  private final Direction dir;
  private final int scatter;
  private boolean done = false;
  private int extensionsRemaining;

  public Edge(LayoutGeneratorClassic layoutGeneratorClassic, Coord start, Direction dir) {
    this.layoutGeneratorClassic = layoutGeneratorClassic;
    this.start = start.copy();
    this.end = start.copy();
    this.dir = dir;
    this.scatter = layoutGeneratorClassic.getScatter();
    this.extensionsRemaining = 2;
  }

  public void update(Random random) {
    if (isDone()) {
      return;
    }

    if (layoutGeneratorClassic.hasNodeNearby(end)) {
      end.translate(getDir(), scatter);
    } else if (random.nextInt(1 + extensionsRemaining) > 0) {
      end.translate(getDir(), scatter);
      extensionsRemaining--;
    } else {
      layoutGeneratorClassic.createNode(this);
      this.done = true;
    }
  }

  public boolean isDone() {
    return done;
  }

  public Direction getDirection() {
    return getDir();
  }

  public Coord getEnd() {
    return end.copy();
  }

  public DungeonTunnel asDungeonTunnel() {
    return new DungeonTunnel(start.copy(), end.copy());
  }

  public Direction getDir() {
    return dir;
  }

}
