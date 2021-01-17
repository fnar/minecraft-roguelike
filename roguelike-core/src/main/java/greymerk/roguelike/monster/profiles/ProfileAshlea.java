package greymerk.roguelike.monster.profiles;

import com.github.srwaggon.util.Color;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileAshlea implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {

    mob.setChild(true);

    MonsterProfile.VILLAGER.getMonsterProfile().addEquipment(world, rand, level, mob);

    ItemStack weapon = ItemNovelty.getItem(ItemNovelty.ASHLEA);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, weapon);

    Slot[] slotsToBeArmored = {Slot.HEAD, Slot.CHEST, Slot.LEGS, Slot.FEET};
    for (Slot slot : slotsToBeArmored) {
      ItemStack item = ItemArmour.get(slot, Quality.WOOD);
      ItemArmour.dyeArmor(item, new Color(255, 100, 255));
      mob.setSlot(Slot.getSlot(slot), item);
    }
  }
}
