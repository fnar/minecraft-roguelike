package greymerk.roguelike.config.migration;

import greymerk.roguelike.config.ConfigurationMap;
import greymerk.roguelike.config.RogueConfig;

public class RenameSpawnAttemptsToDungeonsSpawnAttempts implements RogueConfigMigration {

  public static final RogueConfig DEPRECATED_SPAWN_ATTEMPTS = new RogueConfig("spawnFrequency").withValue(10);

  @Override
  public void accept(ConfigurationMap configurationMap) {
    if (configurationMap.containsKey(DEPRECATED_SPAWN_ATTEMPTS.getName())) {
      if (!configurationMap.containsKey(RogueConfig.DUNGEONS_SPAWN_ATTEMPTS.getName())) {
        RogueConfig.DUNGEONS_SPAWN_ATTEMPTS.setInt(DEPRECATED_SPAWN_ATTEMPTS.getInt());
      }
      configurationMap.remove(DEPRECATED_SPAWN_ATTEMPTS.getName());
    }
  }

}
