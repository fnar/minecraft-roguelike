package greymerk.roguelike.dungeon.segment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class SegmentGenerator implements ISegmentGenerator {

  protected Segment arch;
  protected WeightedRandomizer<Segment> segments;

  public SegmentGenerator() {
    this(Segment.ARCH);
  }

  public SegmentGenerator(Segment arch) {
    segments = new WeightedRandomizer<>();
    this.arch = arch;
  }

  public SegmentGenerator(SegmentGenerator toCopy) {
    arch = toCopy.arch;
    segments = new WeightedRandomizer<>(toCopy.segments);
  }

  public SegmentGenerator(JsonObject json) {
    String archType = json.get("arch").getAsString();
    arch = Segment.valueOf(archType);

    segments = new WeightedRandomizer<>();
    JsonArray segmentList = json.get("segments").getAsJsonArray();
    for (JsonElement jsonElement : segmentList) {
      if (jsonElement.isJsonNull()) {
        continue;
      }
      JsonObject segData = jsonElement.getAsJsonObject();
      add(segData);
    }
  }

  public static SegmentGenerator getRandom(Random rand, int count) {
    SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
    for (int i = 0; i < count; ++i) {
      segments.add(Segment.getRandom(rand), 1);
    }
    return segments;
  }

  public void add(JsonObject entry) {

    String segType = entry.get("type").getAsString();
    Segment segment = Segment.valueOf(segType);

    if (entry.has("arch")) {
      boolean a = entry.get("arch").getAsBoolean();
      if (a) {
        arch = segment;
      }
      return;
    }

    int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;

    segments.add(new WeightedChoice<>(segment, weight));
  }

  public SegmentGenerator inherit(SegmentGenerator toInherit) {
    SegmentGenerator segmentGenerator = new SegmentGenerator();
    segmentGenerator.segments.merge(segments);
    segmentGenerator.segments.merge(toInherit.segments);
    return segmentGenerator;
  }

  public void add(Segment segment, int weight) {
    segments.add(new WeightedChoice<>(segment, weight));
  }

  @Override
  public List<ISegment> genSegment(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Coord pos) {

    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();

    List<ISegment> segments = new ArrayList<>();

    for (Direction direction : dir.orthogonals()) {
      ISegment segment = pickSegment(rand, dir, pos);
      if (segment == null) {
        return segments;
      }
      segment.generate(editor, rand, level, direction, level.getSettings().getTheme(), pos.copy());
      segments.add(segment);
    }

    if (!level.hasNearbyNode(pos) && rand.nextInt(3) == 0) {
      addSupport(editor, rand, level.getSettings().getTheme(), x, y, z);
    }

    return segments;
  }

  private ISegment pickSegment(Random rand, Direction dir, Coord pos) {
    int x = pos.getX();
    int z = pos.getZ();

    if ((dir == Direction.NORTH || dir == Direction.SOUTH) && z % 3 == 0) {
      if (z % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return segments.isEmpty()
          ? Segment.getSegment(Segment.WALL)
          : Segment.getSegment(segments.get(rand));
    }

    if ((dir == Direction.WEST || dir == Direction.EAST) && x % 3 == 0) {
      if (x % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return segments.isEmpty()
          ? Segment.getSegment(Segment.WALL)
          : Segment.getSegment(segments.get(rand));
    }

    return null;
  }

  private void addSupport(WorldEditor editor, Random rand, ThemeBase theme, int x, int y, int z) {
    if (!editor.isAirBlock(new Coord(x, y - 2, z))) {
      return;
    }

    editor.fillDown(new Coord(x, y - 2, z), theme.getPrimary().getPillar());

    StairsBlock stair = theme.getPrimary().getStair();
    stair.setUpsideDown(true).setFacing(Direction.WEST)
        .stroke(editor, new Coord(x - 1, y - 2, z));

    stair.setUpsideDown(true).setFacing(Direction.EAST)
        .stroke(editor, new Coord(x + 1, y - 2, z));

    stair.setUpsideDown(true).setFacing(Direction.SOUTH)
        .stroke(editor, new Coord(x, y - 2, z + 1));

    stair.setUpsideDown(true).setFacing(Direction.NORTH)
        .stroke(editor, new Coord(x, y - 2, z - 1));
  }
}
