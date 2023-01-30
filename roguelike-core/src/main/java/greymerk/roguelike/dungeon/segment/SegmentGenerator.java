package greymerk.roguelike.dungeon.segment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.github.fnar.roguelike.worldgen.generatables.Pillar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.segment.part.SegmentBase;
import greymerk.roguelike.theme.Theme;
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

  public List<SegmentBase> genSegment(WorldEditor editor, DungeonLevel level, Direction dir, Coord pos) {
    List<SegmentBase> segments = new ArrayList<>();

    for (Direction orthogonals : dir.orthogonals()) {
      SegmentBase segment = pickSegment(editor.getRandom(), dir, pos);
      if (segment == null) {
        return segments;
      }
      segment.generate(editor, level, orthogonals, level.getSettings().getTheme(), pos.copy());
      segments.add(segment);
    }

    if (!level.containsRoomAt(pos) && editor.getRandom().nextInt(3) == 0) {
      addSupport(editor, level.getSettings().getTheme(), pos.copy().down(2));
    }

    return segments;
  }

  private SegmentBase pickSegment(Random random, Direction dir, Coord pos) {
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

  private SegmentBase pickSegment(boolean isArch, Random random) {
    return isArch
        ? Segment.getSegment(getArch())
        : getSegments().isEmpty()
            ? Segment.getSegment(Segment.WALL)
            : Segment.getSegment(getSegments().get(random));
  }

  private void addSupport(WorldEditor editor, Theme theme, Coord origin) {
    if (!editor.isAirBlock(origin)) {
      return;
    }

    Pillar.newPillar(editor)
        .withPillar(theme.getPrimary().getPillar())
        .withStairs(theme.getPrimary().getStair())
        .withHeight(1) // start at given location and generate downward
        .generate(origin);
  }

  public SegmentGenerator with(Segment segment, int weight) {
    add(segment, weight);
    return this;
  }

}
