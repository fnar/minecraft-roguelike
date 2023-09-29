package greymerk.roguelike.dungeon.settings;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.layout.LayoutGenerator;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Themes;

public class TestDungeonSettings extends DungeonSettings {

  private final Random random;

  public TestDungeonSettings(Random random) {
    super("testDungeon");
    this.random = random;
    setExclusive(true);
    setTowerSettings(new TowerSettings(TowerType.random(random), Themes.random(random)));
    IntStream.range(0, 5).forEach(this::generateLevelSettings);
  }

  private void generateLevelSettings(int i) {
    LevelSettings levelSettings = getLevelSettings(i);

    levelSettings.setGenerator(i % 2 == 0 ? LayoutGenerator.Type.CLASSIC : LayoutGenerator.Type.MST);
    levelSettings.setRange(60);
    levelSettings.setScatter(15);

    levelSettings.setTheme(Themes.random(random));

    Arrays.stream(RoomType.values()).map(RoomType::newSingleRoomSetting).forEach(levelSettings.getRooms()::add);
    Arrays.stream(RoomType.values()).map(RoomType::newSingleRoomSetting).forEach(levelSettings.getSecrets()::add);
    Arrays.stream(Segment.values()).map(segment -> new SegmentGenerator().with(segment, 1)).forEach(levelSettings.getSegments()::add);
  }

}
