package greymerk.roguelike.dungeon.segment;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class SegmentGeneratorBase implements ISegmentGenerator {

  protected Segment arch;
  protected WeightedRandomizer<Segment> segments;

  public SegmentGeneratorBase() {
    segments = new WeightedRandomizer<>();
    segments.add(new WeightedChoice<>((Segment.SHELF), 1));
    segments.add(new WeightedChoice<>((Segment.INSET), 1));
    segments.add(new WeightedChoice<>((Segment.DOOR), 1));
    segments.add(new WeightedChoice<>((Segment.FIREPLACE), 1));

    arch = Segment.ARCH;
  }

  public void add(Segment toAdd, int weight) {
    segments.add(new WeightedChoice<>(toAdd, weight));
  }

  @Override
  public List<ISegment> genSegment(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, Coord pos) {

    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();

    List<ISegment> segs = new ArrayList<>();

    for (Cardinal orth : dir.orthogonals()) {
      ISegment seg = pickSegment(editor, rand, level, dir, pos);
      if (seg == null) {
        return segs;
      }
      seg.generate(editor, rand, level, orth, level.getSettings().getTheme(), new Coord(pos));
      segs.add(seg);
    }

    if (!level.hasNearbyNode(pos) && rand.nextInt(3) == 0) {
      addSupport(editor, rand, level.getSettings().getTheme(), x, y, z);
    }
    return segs;
  }

  private ISegment pickSegment(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, Coord pos) {
    int x = pos.getX();
    int z = pos.getZ();

    if ((dir == Cardinal.NORTH || dir == Cardinal.SOUTH) && z % 3 == 0) {
      if (z % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return Segment.getSegment(segments.get(rand));
    }

    if ((dir == Cardinal.WEST || dir == Cardinal.EAST) && x % 3 == 0) {
      if (x % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return Segment.getSegment(segments.get(rand));
    }

    return null;
  }

  private void addSupport(WorldEditor editor, Random rand, ThemeBase theme, int x, int y, int z) {
    if (!editor.isAirBlock(new Coord(x, y - 2, z))) {
      return;
    }

    editor.fillDown(new Coord(x, y - 2, z), theme.getPrimary().getPillar());

    StairsBlock stair = theme.getPrimary().getStair();
    stair.setUpsideDown(true).setFacing(Cardinal.WEST);
    stair.stroke(editor, new Coord(x - 1, y - 2, z));

    stair.setUpsideDown(true).setFacing(Cardinal.EAST);
    stair.stroke(editor, new Coord(x + 1, y - 2, z));

    stair.setUpsideDown(true).setFacing(Cardinal.SOUTH);
    stair.stroke(editor, new Coord(x, y - 2, z + 1));

    stair.setUpsideDown(true).setFacing(Cardinal.NORTH);
    stair.stroke(editor, new Coord(x, y - 2, z - 1));
  }
}
