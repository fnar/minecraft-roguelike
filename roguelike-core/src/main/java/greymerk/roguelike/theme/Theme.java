package greymerk.roguelike.theme;

import java.util.Optional;
import java.util.Set;

import greymerk.roguelike.dungeon.settings.SettingsType;

import static greymerk.roguelike.dungeon.settings.SettingsType.THEMES;

public class Theme {

  protected BlockSet primary;
  protected BlockSet secondary;

  public Theme() {
  }

  public Theme(BlockSet primary, BlockSet secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  public Theme inherit(Theme parent, Set<SettingsType> overrides) {
    if (parent == null) {
      return this;
    }
    if (overrides.contains(THEMES)) {
      return this;
    }
    BlockSet primary = BlockSet.inherit(getPrimary(), parent.getPrimary());
    BlockSet secondary = BlockSet.inherit(getSecondary(), parent.getSecondary());
    return new Theme(primary, secondary);
  }

  public BlockSet getPrimary() {
    return primary;
  }

  public BlockSet getSecondary() {
    return Optional.ofNullable(secondary).orElse(getPrimary());
  }

}
