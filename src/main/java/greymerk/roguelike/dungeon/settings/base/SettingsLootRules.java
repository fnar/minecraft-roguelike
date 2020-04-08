package greymerk.roguelike.dungeon.settings.base;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.treasure.loot.LootTableRule;

import static com.google.common.collect.Lists.newArrayList;

public class SettingsLootRules extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "loot");

  public SettingsLootRules() {
    super(ID);
    getLootTables().add(new LootTableRule(newArrayList(), new ResourceLocation(LootTableList.CHESTS_SIMPLE_DUNGEON.getResourcePath()), newArrayList()));
  }
}
