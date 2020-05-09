package greymerk.roguelike.theme;

import java.util.Optional;

public class ThemeBase implements ITheme {

  protected BlockSet primary;
  protected BlockSet secondary;

  public ThemeBase() {
  }

  public ThemeBase(BlockSet primary, BlockSet secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  @Override
  public BlockSet getPrimary() {
    return primary;
  }

  @Override
  public BlockSet getSecondary() {
    return Optional.ofNullable(secondary).orElse(getPrimary());
  }

}
