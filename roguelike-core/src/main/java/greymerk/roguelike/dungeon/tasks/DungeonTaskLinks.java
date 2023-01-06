package greymerk.roguelike.dungeon.tasks;

import com.github.fnar.roguelike.worldgen.generatables.BaseGeneratable;
import com.github.fnar.roguelike.worldgen.generatables.LadderPillar;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStaircase;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskLinks implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {

    List<DungeonLevel> levels = dungeon.getLevels();

    // generate level links
    DungeonLevel previous = null;
    for (DungeonLevel level : levels) {
      DungeonNode upper = previous == null ? null : previous.getLayout().getEnd();
      DungeonNode lower = level.getLayout().getStart();
      LevelSettings lowerSettings = level.getSettings();
      LevelSettings upperSettings = previous != null ? previous.getSettings() : null;
      generateLevelLink(editor, lower, lowerSettings, upper, upperSettings);
      previous = level;
    }

  }

  private void generateLevelLink(WorldEditor editor, DungeonNode lower, LevelSettings lowerSettings, DungeonNode upper, LevelSettings upperSettings) {

    BaseRoom downstairs = RoomType.LINKER.newSingleRoomSetting().instantiate(lowerSettings, editor);
    downstairs.generate(lower.getPosition(), Direction.CARDINAL);

    if (upper == null) {
      return;
    }

    BaseRoom upstairs = RoomType.LINKERTOP.newSingleRoomSetting().instantiate(upperSettings, editor);
    upstairs.generate(upper.getPosition(), upper.getEntrances());

    int height = upper.getPosition().getY() - lower.getPosition().getY();  // should equal Dungeon.VERTICAL_SPACING

    BaseGeneratable linker = (editor.getRandom().nextDouble() < 0.75)
        ? SpiralStaircase.newStaircase(editor).withHeight(height)
        : LadderPillar.newLadderPillar(editor).withHeight(height);

    linker.withTheme(lowerSettings.getTheme()).generate(lower.getPosition());
  }

}
