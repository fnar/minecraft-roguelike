package greymerk.roguelike.dungeon;

import com.github.fnar.roguelike.worldgen.generatables.BaseGeneratable;
import com.github.fnar.roguelike.worldgen.generatables.LadderPillar;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStaircase;

import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public enum LevelGenerator {

  CLASSIC,
  MST;

  public static ILevelGenerator getGenerator(WorldEditor editor, Random rand, LevelGenerator type, DungeonLevel level) {
    switch (type) {
      case CLASSIC:
        return new LevelGeneratorClassic(rand, level.getSettings());
      case MST:
        return new LevelGeneratorMST(editor, rand, level.getSettings());
      default:
        return new LevelGeneratorClassic(rand, level.getSettings());
    }
  }

  public static void generateLevelLink(WorldEditor editor, DungeonNode lower, LevelSettings lowerSettings, DungeonNode upper, LevelSettings upperSettings) {

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
