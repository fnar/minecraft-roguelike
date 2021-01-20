package greymerk.roguelike.dungeon.settings;

import org.junit.Test;

import greymerk.roguelike.dungeon.segment.Segment;

import static org.assertj.core.api.Assertions.assertThat;

public class DungeonSettingsParserTest {

  @Test
  public void segmentsInherit() {
    DungeonSettings parent0 = new DungeonSettings();
    parent0.getLevelSettings().put(0, new LevelSettings());
    parent0.getLevelSettings(0).getSegments().add(Segment.BOOKS, 1);
    DungeonSettings parent1 = new DungeonSettings();
    parent1.getLevelSettings().put(0, new LevelSettings());
    parent1.getLevelSettings(0).getSegments().add(Segment.ARROW, 1);

    DungeonSettings child = new DungeonSettings().inherit(parent0);

    assertThat(child.getLevelSettings(0).getSegments()).isEqualTo(parent0.getLevelSettings(0).getSegments());
  }
}