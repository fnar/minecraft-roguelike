package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BoundingBox;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.Shape;

public class DungeonNode implements Bounded {

  public static final int ENCASING_SIZE = 1;
  private final Coord pos;
  private BaseRoom toGenerate;
  private final List<Direction> entrances;

  public DungeonNode(List<Direction> entrances, Coord origin) {
    this.entrances = entrances;
    pos = origin.copy();
  }

  public void setDungeon(BaseRoom toGenerate) {
    this.toGenerate = toGenerate;
  }

  public int getSize() {
    if (toGenerate == null) {
      return 10;
    }

    return toGenerate.getSize() + ENCASING_SIZE;
  }

  public void encase(WorldEditor editor, Theme theme) {
    int size = getSize();
    int height = 8 + ENCASING_SIZE;
    int depth = 3 + ENCASING_SIZE;
    IShape caseRect = getPosition().copy().newHollowRect(size).withHeight(height + depth).down(depth);
    theme.getPrimary().getWall().fill(editor, caseRect);
  }

  public List<Direction> getEntrances() {
    return entrances;
  }

  public Coord getPosition() {
    return pos.copy();
  }

  public BaseRoom getRoom() {
    return toGenerate;
  }

  public BoundingBox getBoundingBox(int size) {
    Coord start = pos.copy();
    Coord end = pos.copy();

    start.north(size);
    start.west(size);
    start.down();

    end.south(size);
    end.east(size);
    end.up(8);

    return new BoundingBox(start, end);
  }

  public BoundingBox getBoundingBox() {
    return getBoundingBox(getSize());
  }

  public boolean connectsTo(DungeonTunnel tunnel) {
    return tunnel.hasEnd(pos);
  }

  @Override
  public boolean collide(Bounded other) {
    return getBoundingBox().collide(other);
  }

  @Override
  public IShape getShape(Shape type) {
    return getBoundingBox().getShape(type);
  }

  @Override
  public Coord getStart() {
    return getBoundingBox().getStart();
  }

  @Override
  public Coord getEnd() {
    return getBoundingBox().getEnd();
  }

  boolean overlaps(int size, DungeonNode other) {
    return this != other && getBoundingBox(size).collide(other);
  }

  boolean overlaps(int size, DungeonTunnel tunnel) {
    return !connectsTo(tunnel) && getBoundingBox(size).collide(tunnel);
  }

  boolean isNotYetGenerated() {
    return getRoom() == null;
  }

  public void generate() {
    getRoom().generate(getPosition(), getEntrances());
  }

  boolean hasOverlappingNode(int size, List<DungeonNode> nodes) {
    return nodes.stream()
        .anyMatch(other -> overlaps(size, other));
  }

  boolean contains(Coord pos) {
    return (int) getPosition().distance(pos) < getSize();
  }

  public double getDistance(DungeonNode end) {
    return getPosition().distance(end.getPosition());
  }
}
