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
    this.wallDist = prototype.getWallDist();
  }

  @Override
  public boolean isValidLocation(Coord at, Direction facing) {
    if (getRoomSetting().getCount() <= 0) {
      return false;
    }
    int connectionLength = 5;
    Coord cursor = at.copy().translate(facing, prototype.getWallDist() + connectionLength);
    return prototype.isValidLocation(cursor, facing.reverse());
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    Direction entrance = getEntrance(entrances);
    int inset = prototype.getWallDist() + 5;
    Coord secretRoomOrigin = at.copy().translate(entrance, inset);

    RectSolid hallwayWallsRect = RectSolid.newRect(
        at.copy().translate(entrance.left()).translate(entrance, 2).down(),
        at.copy().translate(entrance.right()).translate(entrance, inset).up(2)
    );
    primaryWallBrush().fill(worldEditor, hallwayWallsRect, false, true);

    RectSolid hallwayHollowRect = RectSolid.newRect(at, secretRoomOrigin.copy().up());
    SingleBlockBrush.AIR.fill(worldEditor, hallwayHollowRect);

    return prototype.generate(secretRoomOrigin, Lists.newArrayList(entrance.reverse()));
  }

}
