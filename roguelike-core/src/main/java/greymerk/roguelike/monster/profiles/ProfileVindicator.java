package greymerk.roguelike.monster.profiles;

import com.github.fnar.roguelike.loot.special.tools.SpecialAxe;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;

public class ProfileVindicator implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.VINDICATOR, true);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, new SpecialAxe(random, level).complete());
  }

}
