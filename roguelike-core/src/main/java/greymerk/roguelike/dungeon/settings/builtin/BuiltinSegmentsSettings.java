package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

public class BuiltinSegmentsSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Segments";

  public BuiltinSegmentsSettings() {
    super(ID);

    for (int level = 0; level < 5; ++level) {

      SegmentGenerator segments;

      switch (level) {
        case 0:
          segments = new SegmentGenerator(Segment.ARCH);
          segments.add(Segment.DOOR, 8);
          segments.add(Segment.FLOWERS, 2);
          segments.add(Segment.FIREPLACE, 2);
          segments.add(Segment.LAMP, 2);
          segments.add(Segment.WHEAT, 1);
          break;
        case 1:
          segments = new SegmentGenerator(Segment.ARCH);
          segments.add(Segment.BOOKS, 1);
          segments.add(Segment.CHEST, 1);
          segments.add(Segment.FIREPLACE, 1);
          segments.add(Segment.INSET, 2);
          segments.add(Segment.SHELF, 1);
          segments.add(Segment.SPAWNER, 1);
          segments.add(Segment.WALL, 6);
          break;
        case 2:
          segments = new SegmentGenerator(Segment.ARCH);
          segments.add(Segment.CELL, 1);
          segments.add(Segment.CHEST, 1);
          segments.add(Segment.FIREPLACE, 1);
          segments.add(Segment.INSET, 4);
          segments.add(Segment.SHELF, 4);
          segments.add(Segment.SKULL, 1);
          segments.add(Segment.SPAWNER, 1);
          segments.add(Segment.TOMB, 2);
          segments.add(Segment.WALL, 4);
          break;
        case 3:
          segments = new SegmentGenerator(Segment.MOSSYARCH);
          segments.add(Segment.ARROW, 2);
          segments.add(Segment.CELL, 2);
          segments.add(Segment.CHEST, 1);
          segments.add(Segment.INSET, 3);
          segments.add(Segment.MUSHROOM, 2);
          segments.add(Segment.SHELF, 3);
          segments.add(Segment.SILVERFISH, 1);
          segments.add(Segment.SKULL, 1);
          segments.add(Segment.SPAWNER, 1);
          segments.add(Segment.TOMB, 1);
          break;
        case 4:
          segments = new SegmentGenerator(Segment.NETHERARCH);
          segments.add(Segment.CHEST, 1);
          segments.add(Segment.NETHERLAVA, 3);
          segments.add(Segment.NETHERSTRIPE, 3);
          segments.add(Segment.NETHERWART, 3);
          segments.add(Segment.SKULL, 3);
          segments.add(Segment.SPAWNER, 10);
          break;
        default:
          segments = new SegmentGenerator(Segment.ARCH);
          segments.add(Segment.WALL, 1);
          break;
      }

      LevelSettings levelSettings = new LevelSettings(level);
      levelSettings.setSegments(segments);
      getLevelSettings().put(level, levelSettings);

    }
  }
}
