package greymerk.roguelike.monster.profiles;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.provider.ItemTool;

public class ProfileZombie implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {


    if (level == 4 && rand.nextInt(20) == 0) {
      MonsterProfile.PIGMAN.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (level == 3 && rand.nextInt(100) == 0) {
      MonsterProfile.WITCH.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (level == 2 && rand.nextInt(300) == 0) {
      MonsterProfile.EVOKER.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (level == 1 && rand.nextInt(200) == 0) {
      MonsterProfile.JOHNNY.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (rand.nextInt(100) == 0) {
      MonsterProfile.RLEAHY.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (rand.nextInt(100) == 0) {
      MonsterProfile.ASHLEA.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (rand.nextInt(40) == 0) {
      MonsterProfile.BABY.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (level > 1 && rand.nextInt(20) == 0) {
      MonsterProfile.HUSK.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    if (level < 3 && rand.nextInt(20) == 0) {
      MonsterProfile.VILLAGER.getMonsterProfile().addEquipment(world, rand, level, mob);
      return;
    }

    ItemStack weapon = ItemTool.getRandom(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
    mob.setSlot(EntityEquipmentSlot.MAINHAND, weapon);
    mob.setSlot(EntityEquipmentSlot.OFFHAND, Shield.get(rand));
    MonsterProfile.TALLMOB.getMonsterProfile().addEquipment(world, rand, level, mob);

  }

}
