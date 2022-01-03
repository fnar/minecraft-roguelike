package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.treasure.loot.provider.ToolLootItem;

public class ProfileHusk implements IMonsterProfile {

  @Override
  public void equip(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.HUSK, false);
    mob.equipMainhand(ToolLootItem.getRandom(random, level, IMonsterProfile.canEnchant(world.getDifficulty(), random, level)));
    mob.equipShield(random);
    mob.equipArmor(world, random, level, Color.random());
  }
}
