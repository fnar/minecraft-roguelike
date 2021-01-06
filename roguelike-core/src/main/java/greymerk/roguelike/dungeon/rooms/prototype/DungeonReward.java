package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonReward extends DungeonBase {

  public DungeonReward(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = levelSettings.getTheme();

    RectSolid.newRect(new Coord(x - 7, y, z - 7), new Coord(x + 7, y + 5, z + 7)).fill(worldEditor, SingleBlockBrush.AIR);
    RectHollow.newRect(new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 6, z + 8)).fill(worldEditor, theme.getPrimary().getWall(), false, true);
    RectSolid.newRect(new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 5, z + 1)).fill(worldEditor, theme.getPrimary().getWall());

    Coord cursor;
    Coord start;
    Coord end;

    StairsBlock stair = theme.getPrimary().getStair();

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonal : dir.orthogonals()) {
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 2);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP, 5);
        RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall());
        cursor.translate(dir.reverse());
        stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP, 2);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall());
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP);
        RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall());
        cursor.translate(Cardinal.UP);
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        end.translate(orthogonal);
        RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall());
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall());
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall());

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 8);
        cursor.translate(Cardinal.UP, 2);
        cursor.translate(orthogonal);
        stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor, true, false);
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse(), 2);
        stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.translate(orthogonal, 3);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        end.translate(orthogonal, 2);
        theme.getPrimary().getPillar().fill(worldEditor, new RectSolid(start, end));

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getPillar());

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 3);
        stair.setUpsideDown(false).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(orthogonal, 2);
        stair.setUpsideDown(false).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP, 2);
        stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(orthogonal.reverse(), 2);
        stair.setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(orthogonal, 2);
        stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        end = new Coord(cursor);
        end.translate(orthogonal.reverse(), 2);
        RectSolid.newRect(cursor, end).fill(worldEditor, stair.setUpsideDown(true).setFacing(dir.reverse()));
        cursor.translate(Cardinal.UP);
        end.translate(Cardinal.UP);
        RectSolid.newRect(cursor, end).fill(worldEditor, theme.getPrimary().getWall());
        end.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 4);
        cursor.translate(Cardinal.DOWN);
        BlockType.GLOWSTONE.getBrush().stroke(worldEditor, cursor);

      }

      Cardinal o = dir.antiClockwise();

      start = new Coord(x, y, z);
      start.translate(dir, 6);
      start.translate(o, 6);
      end = new Coord(start);
      end.translate(dir);
      end.translate(o);
      end.translate(Cardinal.UP, 5);
      RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getPillar());

      cursor = new Coord(x, y, z);
      theme.getPrimary().getWall().stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(Cardinal.UP, 4);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

    }

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 4);
    BlockType.GLOWSTONE.getBrush().stroke(worldEditor, cursor);

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP);
    worldEditor.getTreasureChestEditor().createChest(levelSettings.getDifficulty(cursor), cursor, false, getRoomSetting().getChestType().orElse(ChestType.REWARD));
    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
