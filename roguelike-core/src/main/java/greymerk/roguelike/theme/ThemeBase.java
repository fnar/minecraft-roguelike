package greymerk.roguelike.theme;

import java.util.Optional;

public class ThemeBase {

  protected BlockSet primary;
  protected BlockSet secondary;

  public ThemeBase() {
  }

  public ThemeBase(BlockSet palette) {
    this.primary = palette;
    this.secondary = palette;
  }

  public ThemeBase(BlockSet primary, BlockSet secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  public static ThemeBase inherit(ThemeBase parent, ThemeBase child) {
    return new ThemeBase(
        BlockSet.inherit(parent.getPrimary(), child.getPrimary()),
        BlockSet.inherit(parent.getSecondary(), child.getSecondary()));
  }

  public BlockSet getPrimary() {
    return primary;
  }

  public BlockSet getSecondary() {
    return Optional.ofNullable(secondary).orElse(getPrimary());
  }

}
