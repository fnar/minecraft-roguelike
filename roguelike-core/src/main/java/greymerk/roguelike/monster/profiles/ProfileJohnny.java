package greymerk.roguelike.monster.profiles;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;

public class ProfileJohnny implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.VINDICATOR, false);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, ItemSpecialty.createAxe(random, 4));
    MonsterProfile.TALLMOB.getMonsterProfile().addEquipment(world, random, 3, mob);
    mob.setName("Johnny");
  }

}
