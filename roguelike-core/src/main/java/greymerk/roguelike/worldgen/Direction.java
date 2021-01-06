package greymerk.roguelike.worldgen;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockLever.EnumOrientation;
import net.minecraft.util.EnumFacing;

import java.util.Collections;
import java.util.List;
import java.util.Random;


public enum Direction {

  NORTH(EnumFacing.SOUTH, EnumOrientation.SOUTH),
  EAST(EnumFacing.WEST, EnumOrientation.WEST),
  SOUTH(EnumFacing.NORTH, EnumOrientation.NORTH),
  WEST(EnumFacing.EAST, EnumOrientation.EAST),
  UP(EnumFacing.UP, EnumOrientation.UP_X),
  DOWN(EnumFacing.DOWN, EnumOrientation.DOWN_X),
  ;

  private final EnumFacing facing;
  private final EnumOrientation orientation;

  Direction(
      EnumFacing facing,
      EnumOrientation orientation
  ) {
    this.facing = facing;
    this.orientation = orientation;
  }

  public static Direction randomDirection(Random random) {
    return CARDINAL.get(random.nextInt(CARDINAL.size()));
  }

  public EnumFacing getFacing() {
    return facing;
  }

  public EnumOrientation getOrientation() {
    return orientation;
  }

  public static List<Direction> CARDINAL = Collections.unmodifiableList(Lists.newArrayList(NORTH, EAST, SOUTH, WEST));

  public Direction reverse() {
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

  public Direction antiClockwise() {
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

  public Direction clockwise() {
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

  public Direction[] orthogonals() {
    switch (this) {
      case NORTH:
        return new Direction[]{WEST, EAST};
      case SOUTH:
        return new Direction[]{EAST, WEST};
      case EAST:
        return new Direction[]{NORTH, SOUTH};
      case WEST:
        return new Direction[]{SOUTH, NORTH};
      default:
        return new Direction[]{this, this};
    }
  }
}
