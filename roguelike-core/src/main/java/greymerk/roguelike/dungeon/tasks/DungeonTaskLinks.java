package greymerk.roguelike.dungeon.tasks;

import com.github.fnar.roguelike.worldgen.generatables.BaseGeneratable;
import com.github.fnar.roguelike.worldgen.generatables.LadderPillar;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStaircase;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskLinks implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Dungeon dungeon, DungeonSettings settings) {

    List<DungeonLevel> levels = dungeon.getLevels();

    DungeonLevel previous = null;
    for (DungeonLevel level : levels) {
      DungeonNode lower = level.getLayout().getStart();

      lower.generate();

      if (previous != null) {
        DungeonNode upper = previous.getLayout().getEnd();
        upper.generate();

        generateStairsOrLadder(editor, level, lower, upper);
      }

      previous = level;
    }

  }

  private void generateStairsOrLadder(WorldEditor editor, DungeonLevel level, DungeonNode lower, DungeonNode upper) {
    Theme theme = level.getSettings().getTheme();

    int height = upper.getPosition().getY() - lower.getPosition().getY();

    BaseGeneratable linker = (editor.getRandom().nextDouble() < 0.75)
        ? SpiralStaircase.newStaircase(editor).withHeight(height)
        : LadderPillar.newLadderPillar(editor).withHeight(height);

    linker.withTheme(theme).generate(lower.getPosition());
  }

}
