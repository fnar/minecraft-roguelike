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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class SegmentGenerator {

  protected Segment arch;
  protected WeightedRandomizer<Segment> segments;

  public SegmentGenerator() {
    this(Segment.ARCH);
  }

  public SegmentGenerator(Segment arch) {
    segments = new WeightedRandomizer<>();
    this.arch = arch;
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
    segmentGenerator.add(toInherit);
    segmentGenerator.add(this);
    return segmentGenerator;
  }

  public void add(SegmentGenerator segmentGenerator) {
    this.arch = segmentGenerator.arch;
    this.segments.merge(segmentGenerator.segments);
  }

  public void add(Segment segment, int weight) {
    segments.add(new WeightedChoice<>(segment, weight));
  }

  public Segment getArch() {
    return arch;
  }

  public WeightedRandomizer<Segment> getSegments() {
    return segments;
  }

  public List<ISegment> genSegment(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Coord pos) {
    List<ISegment> segments = new ArrayList<>();

    for (Direction orthogonals : dir.orthogonals()) {
      ISegment segment = pickSegment(rand, dir, pos);
      if (segment == null) {
        return segments;
      }
      segment.generate(editor, rand, level, orthogonals, level.getSettings().getTheme(), pos.copy());
      segments.add(segment);
    }

    if (!level.hasNearbyNode(pos) && rand.nextInt(3) == 0) {
      addSupport(editor, level.getSettings().getTheme(), pos.copy());
    }

    return segments;
  }

  private ISegment pickSegment(Random random, Direction dir, Coord pos) {
    int z = pos.getZ();
    if ((dir == Direction.NORTH || dir == Direction.SOUTH) && z % 3 == 0) {
      return pickSegment(z % 6 == 0, random);
    }

    int x = pos.getX();
    if ((dir == Direction.WEST || dir == Direction.EAST) && x % 3 == 0) {
      return pickSegment(x % 6 == 0, random);
    }

    return null;
  }

  private ISegment pickSegment(boolean isArch, Random random) {
    return isArch
        ? Segment.getSegment(getArch())
        : getSegments().isEmpty()
            ? Segment.getSegment(Segment.WALL)
            : Segment.getSegment(getSegments().get(random));
  }

  private void addSupport(WorldEditor editor, ThemeBase theme, Coord origin) {
    Coord beneathWalkway = origin.copy().down(2);
    if (!editor.isAirBlock(beneathWalkway)) {
      return;
    }

    editor.fillDown(beneathWalkway, theme.getPrimary().getPillar());

    StairsBlock stair = theme.getPrimary().getStair().setUpsideDown(true);
    stair.setFacing(Direction.WEST).stroke(editor, beneathWalkway.copy().west());
    stair.setFacing(Direction.EAST).stroke(editor, beneathWalkway.copy().east());
    stair.setFacing(Direction.SOUTH).stroke(editor, beneathWalkway.copy().south());
    stair.setFacing(Direction.NORTH).stroke(editor, beneathWalkway.copy().north());
  }
}
