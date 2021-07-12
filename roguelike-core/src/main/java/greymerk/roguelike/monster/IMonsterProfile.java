package greymerk.roguelike.monster;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.provider.ItemTool;

public interface IMonsterProfile {

  void addEquipment(World world, Random rand, int level, IEntity mob);

  static void equipMob(World world, Random rand, int level, IEntity mob) {
    boolean isEnchanted = Enchant.canEnchant(world.getDifficulty(), rand, level);
    ItemStack weapon = ItemTool.getRandom(rand, level, isEnchanted);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, weapon);
    mob.setSlot(EntityEquipmentSlot.OFFHAND, Shield.get(rand));
    MonsterProfile.TALLMOB.getMonsterProfile().addEquipment(world, rand, level, mob);
  }

}
