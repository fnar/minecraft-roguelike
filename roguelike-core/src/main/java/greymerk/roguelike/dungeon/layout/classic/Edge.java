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
  private boolean done = false;
  private int extend;

  public Edge(LayoutGeneratorClassic layoutGeneratorClassic, Coord start, Direction dir) {
//    System.out.printf("Generating edge at %s%n", start);
    this.layoutGeneratorClassic = layoutGeneratorClassic;
    this.start = start.copy();
    this.end = start.copy();
    this.dir = dir;
    this.extend = layoutGeneratorClassic.getScatter() * 2;
  }

  public void update(Random random) {
    if (done) {
      return;
    }

    if (layoutGeneratorClassic.hasNodeNearby(end)) {
      end.translate(getDir());
    } else if (random.nextInt(extend) == 0) {
      layoutGeneratorClassic.spawnNode(this);
//      System.out.printf("Finishing edge from %s to %s%n", start, end);
      this.done = true;
    } else {
      end.translate(getDir());
      extend--;
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
