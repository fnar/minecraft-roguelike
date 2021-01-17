package greymerk.roguelike.monster.profiles;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Slot;

public class ProfileTallMob implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {
    Slot[] slotsToBeArmored = {Slot.HEAD, Slot.CHEST, Slot.LEGS, Slot.FEET};
    for (Slot slot : slotsToBeArmored) {
      ItemStack item = Loot.getEquipmentBySlot(rand, slot, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
      mob.setSlot(Slot.getSlot(slot), item);
    }

  }

}
