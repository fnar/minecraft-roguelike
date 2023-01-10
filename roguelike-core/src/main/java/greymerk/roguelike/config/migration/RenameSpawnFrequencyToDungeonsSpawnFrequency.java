package greymerk.roguelike.config.migration;

import greymerk.roguelike.config.ConfigurationMap;
import greymerk.roguelike.config.RogueConfig;

public class RenameSpawnFrequencyToDungeonsSpawnFrequency implements RogueConfigMigration {

  public static final RogueConfig DEPRECATED_SPAWNFREQUENCY = new RogueConfig("spawnFrequency").withValue(10);

  @Override
  public void accept(ConfigurationMap configurationMap) {
    if (configurationMap.containsKey(DEPRECATED_SPAWNFREQUENCY.getName())) {
      if (!configurationMap.containsKey(RogueConfig.DUNGEONS_SPAWN_FREQUENCY.getName())) {
        RogueConfig.DUNGEONS_SPAWN_FREQUENCY.setInt(DEPRECATED_SPAWNFREQUENCY.getInt());
      }
      configurationMap.remove(DEPRECATED_SPAWNFREQUENCY.getName());
    }
  }

}
