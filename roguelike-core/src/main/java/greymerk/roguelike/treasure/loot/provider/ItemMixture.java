package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.PotionMixture;

public class ItemMixture extends LootItem {

  private final PotionMixture type;

  public ItemMixture(JsonObject data, int weight) throws Exception {
    super(weight, 0);

    if (!data.has("name")) {
      throw new Exception("Mixture requires a name");
    }

    String name = data.get("name").getAsString();

    try {
      this.type = PotionMixture.valueOf(name.toUpperCase());
    } catch (Exception e) {
      throw new Exception("No such Mixture: " + name);
    }
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return PotionMixture.getPotion(random, type);
  }
}
