package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSpawner extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    Direction[] orthogonals = dir.orthogonals();

    Coord start = origin.copy()
        .translate(dir, 2)
        .translate(orthogonals[0], 1);

    Coord end = origin.copy()
        .translate(dir, 2)
        .translate(orthogonals[1], 1)
        .up(2);

    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end))
        .translate(dir, 1)
        .fill(editor, getSecondaryWall(theme));

    generateDecorativeArch(editor, dir, origin, theme);
    generateSpawner(editor, editor.getRandom(), level, dir, origin, theme);
  }

  private void generateDecorativeArch(WorldEditor editor, Direction dir, Coord origin, Theme theme) {
    for (Direction orthogonal : dir.orthogonals()) {
      Coord cursor = origin.copy()
          .up(2)
          .translate(dir, 2)
          .translate(orthogonal, 1);
      getSecondaryStairs(theme).setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(editor, cursor);
    }
  }

  private void generateSpawner(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Coord origin, Theme theme) {
    Coord spawnerCoord = origin.copy()
        .translate(dir, 4)
        .up(1);

    Spawner spawner = level.getSettings().getSpawnerSettings().isEmpty()
        ? MobType.chooseAmong(MobType.COMMON_MOBS, rand).asSpawner()
        : level.getSettings().getSpawnerSettings().getSpawners().get(editor.getRandom());
    BaseRoom.generateSpawnerSafe(editor, spawner, spawnerCoord);

    BlockBrush panelInFrontOfSpawner = rand.nextInt(Math.max(1, level.getSettings().getLevel())) == 0
        ? BlockType.GLASS.getBrush()
        : getSecondaryWall(theme);
    panelInFrontOfSpawner.stroke(editor, spawnerCoord.translate(dir.reverse()));
  }
}
