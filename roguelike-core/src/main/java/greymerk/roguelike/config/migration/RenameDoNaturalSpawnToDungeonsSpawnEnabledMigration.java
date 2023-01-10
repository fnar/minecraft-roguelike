package greymerk.roguelike.config.migration;

import greymerk.roguelike.config.ConfigurationMap;
import greymerk.roguelike.config.RogueConfig;

public class RenameDoNaturalSpawnToDungeonsSpawnEnabledMigration implements RogueConfigMigration {

  private static final RogueConfig DEPRECATED_DONATURALSPAWN = new RogueConfig("doNaturalSpawn").withValue(true);

  @Override
  public void accept(ConfigurationMap configurationMap) {
    if (configurationMap.containsKey(DEPRECATED_DONATURALSPAWN.getName())) {
      if (!configurationMap.containsKey(RogueConfig.DUNGEONS_SPAWN_ENABLED.getName())) {
        RogueConfig.DUNGEONS_SPAWN_ENABLED.setBoolean(DEPRECATED_DONATURALSPAWN.getBoolean());
      }
      configurationMap.remove(DEPRECATED_DONATURALSPAWN.getName());
    }
  }

}
