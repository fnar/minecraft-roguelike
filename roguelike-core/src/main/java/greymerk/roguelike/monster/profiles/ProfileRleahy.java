package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Colors;

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

    ItemStack item = ItemArmour.create(Slot.FEET, Quality.WOOD, Colors.LEAD);
    mob.setSlot(EntityEquipmentSlot.FEET, item);

    item = ItemArmour.create(Slot.LEGS, Quality.WOOD, Colors.PRUSSIAN_BLUE);
    mob.setSlot(EntityEquipmentSlot.LEGS, item);

    item = ItemArmour.create(Slot.CHEST, Quality.WOOD, Colors.CLASSIC_ROSE);
    mob.setSlot(EntityEquipmentSlot.CHEST, item);
  }
}
