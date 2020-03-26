package greymerk.roguelike.theme;

import java.util.Optional;

public class ThemeBase implements ITheme {

  protected IBlockSet primary;
  protected IBlockSet secondary;

  public ThemeBase() {
  }

  public ThemeBase(IBlockSet primary, IBlockSet secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  @Override
  public IBlockSet getPrimary() {
    return primary;
  }

  @Override
  public IBlockSet getSecondary() {
    return Optional.ofNullable(secondary).orElse(getPrimary());
  }

}
