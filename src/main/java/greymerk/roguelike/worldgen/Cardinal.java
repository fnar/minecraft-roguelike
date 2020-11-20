package greymerk.roguelike.worldgen;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockLever.EnumOrientation;
import net.minecraft.util.EnumFacing;

import java.util.List;
import java.util.Random;


public enum Cardinal {

  NORTH(EnumFacing.SOUTH, EnumOrientation.SOUTH),
  EAST(EnumFacing.WEST, EnumOrientation.WEST),
  SOUTH(EnumFacing.NORTH, EnumOrientation.NORTH),
  WEST(EnumFacing.EAST, EnumOrientation.EAST),
  UP(EnumFacing.UP, EnumOrientation.UP_X),
  DOWN(EnumFacing.DOWN, EnumOrientation.DOWN_X),
  ;

  private final EnumFacing facing;
  private final EnumOrientation orientation;

  Cardinal(
      EnumFacing facing,
      EnumOrientation orientation
  ) {
    this.facing = facing;
    this.orientation = orientation;
  }

  public static Cardinal randomDirection(Random random) {
    return DIRECTIONS.get(random.nextInt(DIRECTIONS.size()));
  }

  public EnumFacing getFacing() {
    return facing;
  }

  public EnumOrientation getOrientation() {
    return orientation;
  }

  public static List<Cardinal> DIRECTIONS = Lists.newArrayList(NORTH, EAST, SOUTH, WEST);

  public Cardinal reverse() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      case SOUTH:
        return NORTH;
      case UP:
        return DOWN;
      default:
      case DOWN:
        return UP;
    }
  }

  public Cardinal antiClockwise() {
    switch (this) {
      case NORTH:
        return WEST;
      case EAST:
        return NORTH;
      case SOUTH:
        return EAST;
      case WEST:
        return SOUTH;
      default:
        return this;
    }
  }

  public Cardinal clockwise() {
    switch (this) {
      case NORTH:
        return EAST;
      case EAST:
        return SOUTH;
      case SOUTH:
        return WEST;
      case WEST:
        return NORTH;
      default:
        return this;
    }
  }

  public Cardinal[] orthogonal() {
    switch (this) {
      case NORTH:
        return new Cardinal[]{WEST, EAST};
      case SOUTH:
        return new Cardinal[]{EAST, WEST};
      case EAST:
        return new Cardinal[]{NORTH, SOUTH};
      case WEST:
        return new Cardinal[]{SOUTH, NORTH};
      default:
        return new Cardinal[]{this, this};
    }
  }
}
