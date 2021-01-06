package greymerk.roguelike.dungeon;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.segment.ISegment;
import greymerk.roguelike.dungeon.segment.ISegmentGenerator;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BoundingBox;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Shape;

public class DungeonTunnel implements Iterable<Coord>, IBounded {

  private Coord start;
  private Coord end;
  private List<ISegment> segments;
  private List<Coord> tunnel;

  public DungeonTunnel(Coord start, Coord end) {
    this.start = start;
    this.end = end;
    tunnel = new RectSolid(start, end).get();
    segments = new ArrayList<ISegment>();
  }

  @Override
  public Iterator<Coord> iterator() {
    return tunnel.iterator();
  }

  public void encase(WorldEditor editor, ThemeBase theme) {
    Coord s;
    Coord e;
    Cardinal dir = getDirection();

    s = start.copy();
    e = end.copy();
    s.translate(dir.antiClockwise(), 3);
    s.translate(Cardinal.UP, 3);
    e.translate(dir.clockwise(), 3);
    e.translate(Cardinal.DOWN, 3);
    RectSolid.newRect(s, e).fill(editor, theme.getPrimary().getWall());
  }

  public void construct(WorldEditor editor, LevelSettings settings) {

    BlockBrush wallBlocks = settings.getTheme().getPrimary().getWall();
    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    BlockJumble bridgeBlocks = new BlockJumble();
    Coord s;
    Coord e;

    bridgeBlocks.addBlock(floor);
    bridgeBlocks.addBlock(SingleBlockBrush.AIR);

    s = start.copy();
    s.translate(Cardinal.NORTH);
    s.translate(Cardinal.EAST);
    e = end.copy();
    e.translate(Cardinal.SOUTH);
    e.translate(Cardinal.WEST);
    e.translate(Cardinal.UP, 2);
    RectSolid.newRect(s, e).fill(editor, SingleBlockBrush.AIR);

    s.translate(Cardinal.NORTH);
    s.translate(Cardinal.EAST);
    s.translate(Cardinal.DOWN);
    e.translate(Cardinal.SOUTH);
    e.translate(Cardinal.WEST);
    e.translate(Cardinal.UP);
    RectHollow.newRect(s, e).fill(editor, wallBlocks, false, true);

    s = start.copy();
    s.translate(Cardinal.NORTH);
    s.translate(Cardinal.EAST);
    s.translate(Cardinal.DOWN);
    e = end.copy();
    e.translate(Cardinal.SOUTH);
    e.translate(Cardinal.WEST);
    e.translate(Cardinal.DOWN);
    RectSolid.newRect(s, e).fill(editor, floor, false, true);
    RectSolid.newRect(s, e).fill(editor, bridgeBlocks, true, false);

    Cardinal dir = getDirection();

    // end of the tunnel;
    Coord location = end.copy();
    location.translate(dir, 1);

    Coord start = location.copy();
    Cardinal[] orth = dir.orthogonals();
    start.translate(orth[0], 2);
    start.translate(Cardinal.UP, 2);
    Coord end = location.copy();
    end.translate(orth[1], 2);
    end.translate(Cardinal.DOWN, 2);

    RectSolid.newRect(start, end).fill(editor, wallBlocks, false, true);

  }

  public Coord[] getEnds() {
    Coord[] toReturn = new Coord[2];
    toReturn[0] = start.copy();
    toReturn[1] = end.copy();
    return toReturn;
  }

  public Cardinal getDirection() {
    return start.dirTo(end);
  }

  public void genSegments(WorldEditor editor, Random rand, DungeonLevel level) {
    LevelSettings settings = level.getSettings();
    ISegmentGenerator segGen = settings.getSegments();
    for (Coord c : this) {
      segments.addAll(segGen.genSegment(editor, rand, level, getDirection(), c));
    }

  }

  public List<ISegment> getSegments() {
    return segments;
  }

  public BoundingBox getBoundingBox() {
    Coord s;
    Coord e;
    Cardinal dir = getDirection();
    s = start.copy();
    e = end.copy();
    s.translate(dir.antiClockwise(), 2);
    s.translate(Cardinal.UP, 3);
    e.translate(dir.clockwise(), 2);
    e.translate(Cardinal.DOWN, 1);
    return new BoundingBox(s, e);
  }

  public boolean hasEnd(Coord pos) {
    return pos.equals(start) || pos.equals(end);
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

  Optional<Cardinal> getEntrance(Coord vertex) {
    Coord start = getStart();
    Coord end = getEnd();
    if (vertex.equals(start)) {
      return Optional.of(start.dirTo(end));
    } else if (vertex.equals(end)) {
      return Optional.of(end.dirTo(start));
    } else {
      return Optional.empty();
    }
  }
}
