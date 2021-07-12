package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemTool;

public class ProfileVillager implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.ZOMBIEVILLAGER, false);
    mob.equipMainhand(ItemTool.getRandom(random, level, Enchant.canEnchant(world.getDifficulty(), random, level)));
    mob.equipShield(random);
    mob.equipArmor(world, random, level, Color.random());
  }

}
