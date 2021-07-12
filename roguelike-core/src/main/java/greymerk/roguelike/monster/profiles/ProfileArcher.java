package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.util.Color;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public class ProfileArcher implements IMonsterProfile {

  @Override
  public void equip(World world, Random rand, int level, IEntity mob) {
    boolean hasEnchantedBow = Enchant.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0;
    mob.equipMainhand(ItemWeapon.getBow(rand, level, hasEnchantedBow));

    boolean hasPoisonTippedArrows = Enchant.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0;
    if (hasPoisonTippedArrows) {
      mob.equipArrows(Arrow.newRandomHarmful(rand));
    }
    mob.equipArmor(world, rand, level, Color.random());
  }

}
