package greymerk.roguelike.theme;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class ThemeBase {

  protected BlockSet primary;
  protected BlockSet secondary;

  public ThemeBase() {
  }

  public ThemeBase(BlockSet primary, BlockSet secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  public static ThemeBase inherit(ThemeBase parent, ThemeBase child) {
    return new ThemeBase(
        getPrimaryBlockSet(parent, child),
        getSecondaryBlockSet(parent, child));
  }

  private static BlockSet getPrimaryBlockSet(ThemeBase parent, ThemeBase child) {
    return inherit(
        parent.getPrimary(),
        child.getPrimary());
  }

  private static BlockSet getSecondaryBlockSet(ThemeBase parent, ThemeBase child) {
    return inherit(
        parent.getSecondary(),
        child.getSecondary());
  }

  private static BlockSet inherit(
      BlockSet parentBlockSet,
      BlockSet childBlockSet
  ) {
    if (parentBlockSet == null && childBlockSet == null) {
      return new BlockSet();
    }
    if (parentBlockSet == null) {
      return childBlockSet;
    }
    if (childBlockSet == null) {
      return parentBlockSet;
    }
    return new BlockSet(
        ofNullable(childBlockSet.getFloor()).orElse(parentBlockSet.getFloor()),
        ofNullable(childBlockSet.getWall()).orElse(parentBlockSet.getWall()),
        ofNullable(childBlockSet.getStair()).orElse(parentBlockSet.getStair()),
        ofNullable(childBlockSet.getPillar()).orElse(parentBlockSet.getPillar()),
        ofNullable(childBlockSet.getDoor()).orElse(parentBlockSet.getDoor()),
        ofNullable(childBlockSet.getLightBlock()).orElse(parentBlockSet.getLightBlock()),
        ofNullable(childBlockSet.getLiquid()).orElse(parentBlockSet.getLiquid()));
  }

  public BlockSet getPrimary() {
    return primary;
  }

  public BlockSet getSecondary() {
    return Optional.ofNullable(secondary).orElse(getPrimary());
  }

}
