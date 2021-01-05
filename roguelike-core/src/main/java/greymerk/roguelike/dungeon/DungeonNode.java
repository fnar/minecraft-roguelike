package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BoundingBox;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Shape;

public class DungeonNode implements IBounded {

  private Coord pos;
  private DungeonBase toGenerate;
  private List<Cardinal> entrances;

  public DungeonNode(List<Cardinal> entrances, Coord origin) {
    this.entrances = entrances;
    pos = new Coord(origin);
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

  public void encase(WorldEditor editor, ThemeBase theme) {
    int size = getSize();
    Coord s = new Coord(getPosition());
    Coord e = new Coord(s);
    s.translate(Cardinal.NORTH, size);
    s.translate(Cardinal.WEST, size);
    s.translate(Cardinal.DOWN, 3);
    e.translate(Cardinal.SOUTH, size);
    e.translate(Cardinal.EAST, size);
    e.translate(Cardinal.UP, 8);
    RectSolid.newRect(s, e).fill(editor, theme.getPrimary().getWall());
  }

  public List<Cardinal> getEntrances() {
    return entrances;
  }

  public Coord getPosition() {
    return new Coord(pos);
  }

  public DungeonBase getRoom() {
    return toGenerate;
  }

  public BoundingBox getBoundingBox(int size) {
    Coord start = new Coord(pos);
    Coord end = new Coord(pos);

    start.translate(Cardinal.NORTH, size);
    start.translate(Cardinal.WEST, size);
    start.translate(Cardinal.DOWN, 1);

    end.translate(Cardinal.SOUTH, size);
    end.translate(Cardinal.EAST, size);
    end.translate(Cardinal.UP, 8);

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
}
