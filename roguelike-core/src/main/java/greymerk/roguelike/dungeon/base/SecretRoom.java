package greymerk.roguelike.dungeon.base;

import com.google.common.collect.Lists;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class SecretRoom extends DungeonBase {

  private final DungeonBase prototype;

  public SecretRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    prototype = getRoomSetting().instantiate(this.levelSettings, this.worldEditor);
  }

  @Override
  public boolean validLocation(WorldEditor editor, Direction dir, Coord pos) {
    if (getRoomSetting().getCount() <= 0) {
      return false;
    }
    Coord cursor = pos.copy();
    cursor.translate(dir, prototype.getSize() + 5);
    return prototype.validLocation(editor, dir, cursor);
  }

  @Override
  public DungeonBase generate(Coord pos, List<Direction> entrances) {
    int size = prototype.getSize();

    Coord start = pos.copy();
    Coord end = pos.copy();
    Direction entrance = entrances.get(0);
    start.translate(entrance.orthogonals()[0]);
    start.translate(Direction.DOWN);
    start.translate(entrance, 2);
    end.translate(entrance.orthogonals()[1]);
    end.translate(entrance, size + 5);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(worldEditor, levelSettings.getTheme().getPrimary().getWall(), false, true);

    end = pos.copy();
    end.translate(entrance, size + 5);
    end.translate(Direction.UP);
    RectSolid.newRect(pos, end).fill(worldEditor, SingleBlockBrush.AIR);

    end.translate(Direction.DOWN);
    return prototype.generate(end, Lists.newArrayList(entrance));
  }

  @Override
  public int getSize() {
    return prototype.getSize();
  }

}
