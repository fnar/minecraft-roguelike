package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.roguelike.loot.special.armour.SpecialArmour;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;

public class ProfileTallMob implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {
    Arrays.stream(ArmourType.values()).forEach(armourType -> {
      EntityEquipmentSlot slot = Slot.getSlot(armourType.asSlot());
      int enchantLevel = Enchant.canEnchant(world.getDifficulty(), rand, level) ? Enchant.getLevel(rand, level) : 0;
      boolean isSpecialArmour = enchantLevel > 0 && rand.nextInt(20 + (level * 10)) == 0;
      ItemStack item = isSpecialArmour
          ? SpecialArmour.createArmour(rand, level)
          : ItemArmour.getRandom(rand, level, enchantLevel, armourType);
      mob.setSlot(slot, item);
    });

  }

}
