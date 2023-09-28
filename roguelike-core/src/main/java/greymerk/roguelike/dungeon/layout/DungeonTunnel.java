package greymerk.roguelike.dungeon.layout;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.segment.part.SegmentBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Shape;

public class DungeonTunnel implements Iterable<Coord>, Bounded {

  private final Coord start;
  private final Coord end;
  private final List<SegmentBase> segments;
  private final List<Coord> tunnel;

  public DungeonTunnel(Coord start, Coord end) {
    this.start = start;
    this.end = end;
    tunnel = RectSolid.newRect(start, end).get();
    segments = new ArrayList<>();
  }

  @Override
  public Iterator<Coord> iterator() {
    return tunnel.iterator();
  }

  public void encase(WorldEditor editor, Theme theme) {
    Direction dir = getDirection();
    Coord s = start.copy().translate(dir.antiClockwise(), 3).up(3);
    Coord e = end.copy().translate(dir.clockwise(), 3).down(3);
    RectSolid.newRect(s, e).fill(editor, theme.getPrimary().getWall());
  }

  public void construct(WorldEditor editor, LevelSettings settings) {
    generateHollow(editor);
    generateWalls(editor, settings);
    generateFloorAndBridges(editor, settings);
    generateEndOfTunnel(editor, settings);
  }

  private void generateHollow(WorldEditor editor) {
    Coord s = start.copy().north().east();
    Coord e = end.copy().south().west().up(2);
    RectSolid.newRect(s, e).fill(editor, SingleBlockBrush.AIR);
//    ColoredBlock.wool().green().stroke(editor, s);
//    ColoredBlock.wool().red().stroke(editor, e);
  }

  private void generateWalls(WorldEditor editor, LevelSettings settings) {
    Coord s = start.copy().north().east().north().east().down();
    Coord e = end.copy().south().west().up(2).south().west().up();
    RectHollow.newRect(s, e).fill(editor, settings.getTheme().getPrimary().getWall(), false, true);
  }

  private void generateFloorAndBridges(WorldEditor editor, LevelSettings settings) {
    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    BlockJumble bridgeBlocks = new BlockJumble().with(floor).with(SingleBlockBrush.AIR);
    Coord s = start.copy().north().east().down();
    Coord e = end.copy().south().west().down();
    RectSolid.newRect(s, e).fill(editor, floor, false, true);
    RectSolid.newRect(s, e).fill(editor, bridgeBlocks, true, false);
  }

  private void generateEndOfTunnel(WorldEditor editor, LevelSettings settings) {
    Direction dir = getDirection();
    Coord endOfTunnel = end.copy().translate(dir, 1);
    Coord start = endOfTunnel.copy().translate(dir.left(), 2).up(2);
    Coord end = endOfTunnel.copy().translate(dir.right(), 2).down(2);
    RectSolid.newRect(start, end).fill(editor, settings.getTheme().getPrimary().getWall(), false, true);
  }

  public Coord[] getEnds() {
    Coord[] toReturn = new Coord[2];
    toReturn[0] = start.copy();
    toReturn[1] = end.copy();
    return toReturn;
  }

  public Direction getDirection() {
    return start.dirTo(end);
  }

  public void genSegments(WorldEditor editor, DungeonLevel level) {
    LevelSettings settings = level.getSettings();
    SegmentGenerator segGen = settings.getSegments();
    for (Coord c : this) {
      segments.addAll(segGen.genSegment(editor, level, getDirection(), c));
    }

  }

  public List<SegmentBase> getSegments() {
    return segments;
  }

  private Bounded getBoundingBox() {
    Direction dir = getDirection();
    Coord start = this.start.copy()
        .translate(dir.left(), 2)
        .down();
    Coord end = this.end.copy()
        .translate(dir.right(), 2);

    return RectSolid.newRect(start, end).withHeight(Dungeon.VERTICAL_SPACING);
  }

  public boolean hasEnd(Coord pos) {
    return pos.equals(start) || pos.equals(end);
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

  public Optional<Direction> getEntrance(Coord vertex) {
    Coord start = this.start;
    Coord end = this.end;
    if (vertex.equals(start)) {
      return Optional.of(start.dirTo(end));
    } else if (vertex.equals(end)) {
      return Optional.of(end.dirTo(start));
    } else {
      return Optional.empty();
    }
  }
}
