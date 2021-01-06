package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentDoor extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor = origin.copy();
    Coord start;
    Coord end;

    Direction[] orthogonal = dir.orthogonals();

    cursor.translate(dir, 2);
    start = cursor.copy();
    start.translate(orthogonal[0], 1);
    end = cursor.copy();
    end.translate(orthogonal[1], 1);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    SecretsSetting secrets = level.getSettings().getSecrets();
    Optional<DungeonBase> secretMaybe = generateSecret(secrets, editor, level.getSettings(), dir, origin.copy());

    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall(), false, true);

    cursor.translate(Direction.UP, 2);
    for (Direction d : orthogonal) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    if (secretMaybe.isPresent()) {
      cursor = origin.copy();
      cursor.translate(dir, 3);
      theme.getSecondary().getDoor().setFacing(dir.reverse()).stroke(editor, cursor);
    }
  }
}
