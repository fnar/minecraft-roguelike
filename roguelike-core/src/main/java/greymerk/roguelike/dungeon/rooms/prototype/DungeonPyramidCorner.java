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
    ThemeBase theme = levelSettings.getTheme();

    BlockBrush blocks = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();

    RectHollow.newRect(new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y + 3, z + 3)).fill(worldEditor, blocks, false, true);
    RectSolid.newRect(new Coord(x - 2, y + 3, z - 2), new Coord(x + 2, y + 5, z + 2)).fill(worldEditor, blocks, false, true);
    RectSolid.newRect(new Coord(x - 1, y + 3, z - 1), new Coord(x + 1, y + 3, z + 1)).fill(worldEditor, SingleBlockBrush.AIR);

    // floor
    RectSolid.newRect(new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y - 1, z + 3)).fill(worldEditor, theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(Cardinal.UP, 1);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);


    for (Cardinal dir : Cardinal.DIRECTIONS) {

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(dir.orthogonals()[0], 2);
      start = cursor.copy();
      cursor.translate(Cardinal.UP, 2);
      end = cursor.copy();
      RectSolid.newRect(start, end).fill(worldEditor, pillar);
    }

    return this;
  }

  public int getSize() {
    return 4;
  }

}
