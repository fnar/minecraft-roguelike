package greymerk.roguelike.dungeon.segment.part;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerDoor extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
    MetaBlock leaves = Leaves.get(Wood.SPRUCE, false);
    MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    bars.set(editor, cursor);
    start = new Coord(cursor);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    stair.setOrientation(orth[0], true).set(editor, start);
    stair.setOrientation(orth[1], true).set(editor, end);
    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    bars.set(editor, cursor);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, water);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 3);
    bars.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    leaves.set(editor, rand, cursor, false, true);
    cursor.translate(dir);
    water.set(editor, rand, cursor, false, true);
    cursor.translate(dir);
    glowstone.set(editor, rand, cursor, false, true);

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    start = new Coord(cursor);
    start.translate(orth[0], 1);
    end = new Coord(cursor);
    end.translate(orth[1], 1);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    SecretsSetting secrets = level.getSettings().getSecrets();
    Optional<DungeonBase> room = generateSecret(secrets, editor, rand, level.getSettings(), dir, new Coord(origin));

    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);

    cursor.translate(Cardinal.UP, 2);
    for (Cardinal d : orth) {
      Coord c = new Coord(cursor);
      c.translate(d, 1);
      stair.setOrientation(d.reverse(), true);
      stair.set(editor, rand, c);
    }

    if (room.isPresent()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      theme.getSecondary().getDoor().generate(editor, cursor, dir.reverse());
    }
  }
}
