package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class SegmentSpawner extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {
    Direction[] orthogonals = dir.orthogonals();

    Coord start = origin.copy()
        .translate(dir, 2)
        .translate(orthogonals[0], 1);

    Coord end = origin.copy()
        .translate(dir, 2)
        .translate(orthogonals[1], 1)
        .up(2);

    RectSolid.newRect(start, end)
        .fill(editor, SingleBlockBrush.AIR)
        .translate(dir, 1)
        .fill(editor, theme.getSecondary().getWall());

    generateDecorativeArch(editor, dir, origin, theme);
    generateSpawner(editor, rand, level, dir, origin, theme);
  }

  private void generateDecorativeArch(WorldEditor editor, Direction dir, Coord origin, ThemeBase theme) {
    for (Direction orthogonal : dir.orthogonals()) {
      Coord cursor = origin.copy()
          .up(2)
          .translate(dir, 2)
          .translate(orthogonal, 1);
      theme.getSecondary().getStair().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(editor, cursor);
    }
  }

  private void generateSpawner(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Coord origin, ThemeBase theme) {
    Coord spawnerCoord = origin.copy()
        .translate(dir, 4)
        .up(1);

    int difficulty = level.getSettings().getDifficulty(spawnerCoord);

    SpawnerSettings spawners = level.getSettings().getSpawners().isEmpty()
        ? MobType.newSpawnerSetting(MobType.COMMON_MOBS)
        : level.getSettings().getSpawners();
    spawners.generateSpawner(editor, spawnerCoord, difficulty);

    BlockBrush panelInFrontOfSpawner = rand.nextInt(Math.max(1, difficulty)) == 0
        ? BlockType.GLASS.getBrush()
        : theme.getSecondary().getWall();
    panelInFrontOfSpawner.stroke(editor, spawnerCoord.translate(dir.reverse()));
  }
}
