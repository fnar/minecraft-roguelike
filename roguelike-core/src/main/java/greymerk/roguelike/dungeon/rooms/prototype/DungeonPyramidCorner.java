package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonPyramidCorner extends DungeonBase {

  public DungeonPyramidCorner(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = settings.getTheme();

    BlockBrush blocks = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();

    RectHollow.newRect(new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y + 3, z + 3)).fill(editor, blocks, false, true);
    RectSolid.newRect(new Coord(x - 2, y + 3, z - 2), new Coord(x + 2, y + 5, z + 2)).fill(editor, blocks, false, true);
    RectSolid.newRect(new Coord(x - 1, y + 3, z - 1), new Coord(x + 1, y + 3, z + 1)).fill(editor, SingleBlockBrush.AIR);

    // floor
    RectSolid.newRect(new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y - 1, z + 3)).fill(editor, theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 4);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    SingleBlockBrush.AIR.stroke(editor, cursor);


    for (Cardinal dir : Cardinal.DIRECTIONS) {

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      SingleBlockBrush.AIR.stroke(editor, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(dir.orthogonals()[0], 2);
      start = new Coord(cursor);
      cursor.translate(Cardinal.UP, 2);
      end = new Coord(cursor);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }

    return this;
  }

  public int getSize() {
    return 4;
  }

}
