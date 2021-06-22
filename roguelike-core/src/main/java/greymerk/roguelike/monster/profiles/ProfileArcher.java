package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.ItemMapper1_12;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public class ProfileArcher implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {
    boolean hasEnchantedBow = Enchant.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0;
    mob.setSlot(EntityEquipmentSlot.MAINHAND, ItemWeapon.getBow(rand, level, hasEnchantedBow));

    boolean hasPoisonTippedArrows = Enchant.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0;
    if (hasPoisonTippedArrows) {
      mob.setSlot(EntityEquipmentSlot.OFFHAND, ItemMapper1_12.map(Arrow.newRandomHarmful(rand).asItemStack()));
    }
    MonsterProfile.TALLMOB.getMonsterProfile().addEquipment(world, rand, level, mob);
  }

}
