package greymerk.roguelike.monster.profiles;

import com.github.srwaggon.util.Color;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileRleahy implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {
    ItemStack weapon = ItemNovelty.getItem(ItemNovelty.RLEAHY);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, weapon);
    mob.setSlot(EntityEquipmentSlot.OFFHAND, Shield.get(rand));

    ItemStack item = ItemArmour.get(Slot.FEET, Quality.WOOD);
    ItemArmour.dyeArmor(item, new Color(32, 32, 32));
    mob.setSlot(EntityEquipmentSlot.FEET, item);

    item = ItemArmour.get(Slot.LEGS, Quality.WOOD);
    ItemArmour.dyeArmor(item, new Color(0, 51, 102));
    mob.setSlot(EntityEquipmentSlot.LEGS, item);

    item = ItemArmour.get(Slot.CHEST, Quality.WOOD);
    ItemArmour.dyeArmor(item, new Color(255, 204, 229));
    mob.setSlot(EntityEquipmentSlot.CHEST, item);
  }
}
