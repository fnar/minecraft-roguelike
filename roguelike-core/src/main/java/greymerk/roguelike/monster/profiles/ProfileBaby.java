package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.ToolLootItem;

public class ProfileBaby implements IMonsterProfile {

  @Override
  public void equip(World world, Random rand, int level, IEntity mob) {
    mob.setChild(true);

    if (rand.nextBoolean()) {
      MonsterProfile.VILLAGER.getMonsterProfile().equip(world, rand, level, mob);
    }

    boolean isEnchanted = IMonsterProfile.canEnchant(world.getDifficulty(), rand, level);
    RldItemStack weapon = ToolLootItem.getRandom(rand, level, isEnchanted);
    mob.equipMainhand(weapon);
  }

}
