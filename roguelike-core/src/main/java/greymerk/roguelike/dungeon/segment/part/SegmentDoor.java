package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.Optional;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentDoor extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction outward, Theme theme, Coord origin) {
    StairsBlock stair = getSecondaryStairs(theme);
    Direction[] orthogonal = outward.orthogonals();

    Coord cursor = origin.copy();
    cursor.translate(outward, 2);
    Coord start = cursor.copy();
    start.translate(orthogonal[0], 1);
    Coord end = cursor.copy();
    end.translate(orthogonal[1], 1);
    end.up(2);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    SecretsSetting secrets = level.getSettings().getSecrets();
    Optional<BaseRoom> secretMaybe = generateSecret(secrets, editor, level.getSettings(), outward, origin.copy());

    start.translate(outward, 1);
    end.translate(outward, 1);
    RectSolid.newRect(start, end).fill(editor, getSecondaryWall(theme), false, true);

    cursor.up(2);
    for (Direction d : orthogonal) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    if (secretMaybe.isPresent()) {
      cursor = origin.copy();
      cursor.translate(outward, 3);
      getSecondaryDoor(theme).setFacing(outward.reverse()).stroke(editor, cursor);
    }
  }
}
