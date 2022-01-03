package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.util.Color;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class ProfileArcher implements IMonsterProfile {

  @Override
  public void equip(World world, Random rand, int level, IEntity mob) {
    boolean hasEnchantedBow = IMonsterProfile.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0;
    mob.equipMainhand(WeaponLootItem.getBow(rand, level, hasEnchantedBow));

    boolean hasPoisonTippedArrows = IMonsterProfile.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0;
    if (hasPoisonTippedArrows) {
      mob.equipArrows(Arrow.newRandomHarmful(rand));
    }
    mob.equipArmor(world, rand, level, Color.random());
  }

}
