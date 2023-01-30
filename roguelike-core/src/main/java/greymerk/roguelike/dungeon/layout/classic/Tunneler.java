package greymerk.roguelike.dungeon.layout.classic;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.layout.DungeonTunnel;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class Tunneler {

  private final LayoutGeneratorClassic layoutGeneratorClassic;
  private boolean done;
  private final Direction dir;
  private final Coord start;
  private final Coord end;
  private final int scatter;
  private int extend;

  public Tunneler(LayoutGeneratorClassic layoutGeneratorClassic, Direction dir, Coord start, int scatter) {
    this.layoutGeneratorClassic = layoutGeneratorClassic;
    setDone(false);
    this.dir = dir;
    this.start = start.copy();
    end = start.copy();
    this.scatter = scatter;
    extend = scatter * 2;
  }

  public void update(List<Node> nodes, Random random) {
    if (isDone()) {
      return;
    }

    if (layoutGeneratorClassic.hasNearbyNode(nodes, end, scatter)) {
      end.translate(getDir());
    } else {
      if (random.nextInt(extend) == 0) {
        layoutGeneratorClassic.spawnNode(nodes, this);
        setDone(true);
      } else {
        end.translate(getDir());
        extend--;
      }
    }
  }

  public boolean isDone() {
    return done;
  }

  public Direction getDirection() {
    return getDir();
  }

  public Coord getPosition() {
    return end.copy();
  }

  public DungeonTunnel createTunnel() {
    return new DungeonTunnel(start.copy(), end.copy());
  }

  public Direction getDir() {
    return dir;
  }

  public void setDone(boolean done) {
    this.done = done;
  }
}
