package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BoundingBox;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Shape;

public class DungeonNode implements IBounded {

  private final Coord pos;
  private DungeonBase toGenerate;
  private final List<Direction> entrances;

  public DungeonNode(List<Direction> entrances, Coord origin) {
    this.entrances = entrances;
    pos = origin.copy();
  }

  public void setDungeon(DungeonBase toGenerate) {
    this.toGenerate = toGenerate;
  }

  public int getSize() {
    if (toGenerate == null) {
      return 6;
    }

    return toGenerate.getSize();
  }

  public void encase(WorldEditor editor, Theme theme) {
    int size = getSize();
    Coord s = getPosition().copy();
    Coord e = s.copy();
    s.north(size);
    s.west(size);
    s.down(3);
    e.south(size);
    e.east(size);
    e.up(8);
    RectSolid.newRect(s, e).fill(editor, theme.getPrimary().getWall());
  }

  public List<Direction> getEntrances() {
    return entrances;
  }

  public Coord getPosition() {
    return pos.copy();
  }

  public DungeonBase getRoom() {
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
  public boolean collide(IBounded other) {
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
}
