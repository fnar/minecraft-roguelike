package greymerk.roguelike.config.migration;

import greymerk.roguelike.config.ConfigurationMap;
import greymerk.roguelike.config.RogueConfig;

public class RenameRogueSpawnersToMobsProfilesEnabledMigration implements RogueConfigMigration {

  private static final RogueConfig DEPRECATED_ROGUESPAWNERS = new RogueConfig("rogueSpawners").withValue(true);

  @Override
  public void accept(ConfigurationMap configurationMap) {
    if (configurationMap.containsKey(DEPRECATED_ROGUESPAWNERS.getName())) {
      if (!configurationMap.containsKey(RogueConfig.MOBS_PROFILES_ENABLED.getName())) {
        RogueConfig.MOBS_PROFILES_ENABLED.setBoolean(DEPRECATED_ROGUESPAWNERS.getBoolean());
      }
      configurationMap.remove(DEPRECATED_ROGUESPAWNERS.getName());
    }
  }

}
