package greymerk.roguelike.monster.profiles;

import com.github.fnar.roguelike.loot.special.tools.SpecialAxe;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.monster.MonsterProfile;

public class ProfileJohnny implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.VINDICATOR, false);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, new SpecialAxe(random, 4).complete());
    MonsterProfile.TALLMOB.getMonsterProfile().addEquipment(world, random, 3, mob);
    mob.setName("Johnny");
  }

}
