package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerDoor extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();
    BlockBrush water = BlockType.WATER_FLOWING.getBrush();
    BlockBrush leaves = Wood.SPRUCE.getLeaves();
    BlockBrush glowstone = BlockType.GLOWSTONE.getBrush();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orthogonal = dir.orthogonals();

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    bars.stroke(editor, cursor);
    start = new Coord(cursor);
    end = new Coord(start);
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    stair.setUpsideDown(true).setFacing(orthogonal[0]).stroke(editor, start);
    stair.setUpsideDown(true).setFacing(orthogonal[1]).stroke(editor, end);
    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    bars.stroke(editor, cursor);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.newRect(start, end).fill(editor, water);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 3);
    bars.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    leaves.stroke(editor, cursor, false, true);
    cursor.translate(dir);
    water.stroke(editor, cursor, false, true);
    cursor.translate(dir);
    glowstone.stroke(editor, cursor, false, true);

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    start = new Coord(cursor);
    start.translate(orthogonal[0], 1);
    end = new Coord(cursor);
    end.translate(orthogonal[1], 1);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    SecretsSetting secrets = level.getSettings().getSecrets();
    Optional<DungeonBase> room = generateSecret(secrets, editor, level.getSettings(), dir, new Coord(origin));

    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall(), false, true);

    cursor.translate(Cardinal.UP, 2);
    for (Cardinal d : orthogonal) {
      Coord c = new Coord(cursor);
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    if (room.isPresent()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      theme.getSecondary().getDoor().setFacing(dir.reverse()).stroke(editor, cursor);
    }
  }
}
