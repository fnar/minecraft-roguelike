package greymerk.roguelike.config.migration;

import greymerk.roguelike.config.ConfigurationMap;
import greymerk.roguelike.config.RogueConfig;

public class RenameSpawnChanceToDungeonsSpawnChanceMigration implements RogueConfigMigration {

  private static final RogueConfig DEPRECATED_SPAWNCHANCE = new RogueConfig("spawnChance").withValue(1.0);

  @Override
  public void accept(ConfigurationMap configurationMap) {
    if (configurationMap.containsKey(DEPRECATED_SPAWNCHANCE.getName())) {
      if (!configurationMap.containsKey(RogueConfig.DUNGEONS_SPAWN_CHANCE.getName())) {
        RogueConfig.DUNGEONS_SPAWN_CHANCE.setDouble(DEPRECATED_SPAWNCHANCE.getDouble());
      }
      configurationMap.remove(DEPRECATED_SPAWNCHANCE.getName());
    }
  }

}
