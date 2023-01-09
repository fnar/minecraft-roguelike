package greymerk.roguelike.config.migration;

import greymerk.roguelike.config.ConfigurationMap;
import greymerk.roguelike.config.RogueConfig;

public class LootingToMobsItemsDropChanceMigration implements RogueConfigMigration {

  public static final RogueConfig DEPRECATED_LOOTING = new RogueConfig("looting").withValue(0.085D);

  @Override
  public void accept(ConfigurationMap configurationMap) {
    if (configurationMap.containsKey(DEPRECATED_LOOTING.getName())) {
      if (!configurationMap.containsKey(RogueConfig.MOBS_ITEMS_DROP_CHANCE.getName())) {
        RogueConfig.MOBS_ITEMS_DROP_CHANCE.setDouble(DEPRECATED_LOOTING.getDouble());
      }
      configurationMap.remove(DEPRECATED_LOOTING.getName());
    }
  }

}
