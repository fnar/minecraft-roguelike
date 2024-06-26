package greymerk.roguelike.dungeon.layout;

import com.github.fnar.util.ReportThisIssueException;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
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

  private Bounded getBoundingBox(int size) {
    return pos.copy().down().newRect(size).withHeight(Dungeon.VERTICAL_SPACING);
  }

  private Bounded getBoundingBox() {
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

  public boolean overlaps(int size, DungeonNode other) {
    return this != other && getBoundingBox(size).collide(other);
  }

  public boolean overlaps(int size, DungeonTunnel tunnel) {
    return !connectsTo(tunnel) && getBoundingBox(size).collide(tunnel);
  }

  public boolean isNotYetGenerated() {
    return getRoom() == null;
  }

  public void generate() {
    try {
      getRoom().generate(getPosition(), getEntrances());
    } catch (Exception exception) {
      new ReportThisIssueException(exception).printStackTrace();
    }
  }

  public boolean hasOverlappingNode(int size, List<DungeonNode> nodes) {
    return nodes.stream()
        .anyMatch(other -> overlaps(size, other));
  }

  public boolean contains(Coord pos) {
    return (int) getPosition().distance(pos) < getSize();
  }

  public double getDistance(DungeonNode end) {
    return getPosition().distance(end.getPosition());
  }
}
