package greymerk.roguelike.dungeon;

import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
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

  public static void generateLevelLink(WorldEditor editor, Random rand, LevelSettings settings, DungeonNode start, DungeonNode end) {

    BaseRoom downstairs = RoomType.LINKER.newSingleRoomSetting().instantiate(settings, editor);
    downstairs.generate(start.getPosition(), Direction.CARDINAL);

    if (end == null) {
      return;
    }

    BaseRoom upstairs = RoomType.LINKERTOP.newSingleRoomSetting().instantiate(settings, editor);
    upstairs.generate(end.getPosition(), end.getEntrances());

    StairsBlock stair = settings.getTheme().getPrimary().getStair();

    Coord cursor = start.getPosition().copy();
    for (int i = 0; i < end.getPosition().getY() - start.getPosition().getY(); i++) {
      editor.spiralStairStep(rand, cursor, stair, settings.getTheme().getPrimary().getPillar());
      cursor.up();
    }
  }
}
