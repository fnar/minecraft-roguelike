package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.util.Colors;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileAshlea implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {

    mob.setChild(true);

    MonsterProfile.VILLAGER.getMonsterProfile().addEquipment(world, rand, level, mob);

    ItemStack weapon = ItemNovelty.getItem(ItemNovelty.ASHLEA);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, weapon);

    Arrays.stream(ArmourType.values()).forEach(armourType -> {
      EntityEquipmentSlot slot = Slot.getSlot(armourType.asSlot());
      ItemStack item = ItemArmour.create(armourType, Quality.WOOD, Colors.PINK_FLAMINGO);
      mob.setSlot(slot, item);
    });
  }
}
