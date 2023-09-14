package com.github.fnar.roguelike.dungeon;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.roguelike.dungeon.settings.fixture.Layout;
import com.github.fnar.roguelike.dungeon.settings.fixture.Theme;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class DungeonIntegrationTest {

  private SettingsResolver settingsResolver;

  private final ModLoader modLoader = requiredModName -> false;

  @Before
  public void setUp() throws Exception {
    Bootstrap.register();
    RogueConfig.testing = true;

    SettingsContainer settingsContainer = populatedSettingsContainer();

    settingsResolver = new SettingsResolver(settingsContainer);
  }

  private SettingsContainer populatedSettingsContainer() throws Exception {
    SettingsContainer settingsContainer = new SettingsContainer(modLoader);

    settingsContainer.put(com.github.fnar.roguelike.dungeon.settings.fixture.Dungeon.dungeonGenericSettingsJson());
    settingsContainer.put(com.github.fnar.roguelike.dungeon.settings.fixture.Dungeon.dungeonCaveSettingsJson());
    settingsContainer.put(com.github.fnar.roguelike.dungeon.settings.fixture.Dungeon.dungeonCaveSmallSettingsJson());
    settingsContainer.put(com.github.fnar.roguelike.dungeon.settings.fixture.Dungeon.dungeonForestTempleSettingsJson());

    settingsContainer.put(Layout.layoutSizeSmallSettingsJson());
    settingsContainer.put(Layout.layoutRoomcountSmallSettingsJson());
    settingsContainer.put(Layout.layoutScatterSmallSettingsJson());
    settingsContainer.put(Layout.layoutTypeMixedSettingsJson());

    settingsContainer.put(Theme.caveThemeSettingsJson());
    settingsContainer.put(Theme.forestThemeSettingsJson());

    return settingsContainer;
  }

  @Test
  public void immediateThemesInheritCorrectly() {
    assertThat(settingsResolver.getByName("fnar:dungeon_cave").getLevelSettings(0).getTheme())
        .isEqualTo(settingsResolver.getByName("theme:cave").getLevelSettings(0).getTheme());
  }

  @Test
  public void inheritedThemesAreNotOverwrittenBySubsequentInheritances() {
    // the small cave dungeon should retain the cave theme that it inherited from the normal cave dungeon
    assertThat(settingsResolver.getByName("fnar:dungeon_cave_small").getLevelSettings(0).getTheme())
        .isEqualTo(settingsResolver.getByName("theme:cave").getLevelSettings(0).getTheme());
  }
}
