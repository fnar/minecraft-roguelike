package greymerk.roguelike.dungeon.base;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class SecretRoom extends BaseRoom {

  private final BaseRoom prototype;

  public SecretRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    prototype = getRoomSetting().instantiate(this.levelSettings, this.worldEditor);
  }

  @Override
  public boolean isValidLocation(Coord at, Direction facing) {
    if (getRoomSetting().getCount() <= 0) {
      return false;
    }
    int connectionLength = 5;
    Coord cursor = at.copy().translate(facing, prototype.getSize() + connectionLength);
    return prototype.isValidLocation(cursor, facing.reverse());
  }

  @Override
  public BaseRoom generate(Coord pos, List<Direction> entrances) {
    int size = prototype.getSize();

    Coord start = pos.copy();
    Coord end = pos.copy();
    Direction entrance = getEntrance(entrances);
    start.translate(entrance.orthogonals()[0]);
    start.down();
    start.translate(entrance, 2);
    end.translate(entrance.orthogonals()[1]);
    end.translate(entrance, size + 5);
    end.up(2);
    RectSolid.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

    end = pos.copy();
    end.translate(entrance, size + 5);
    end.up();
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(pos, end));

    end.down();
    return prototype.generate(end, Lists.newArrayList(entrance.reverse()));
  }

  @Override
  public int getSize() {
    return prototype.getSize();
  }

}
