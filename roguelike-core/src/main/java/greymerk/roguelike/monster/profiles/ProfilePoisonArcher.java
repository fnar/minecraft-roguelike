package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.util.Colors;
import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.ItemMapper1_12;
import com.github.fnar.minecraft.item.Potion;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public class ProfilePoisonArcher implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {

    mob.setMobClass(MobType.STRAY, false);

    mob.setSlot(EntityEquipmentSlot.OFFHAND, ItemMapper1_12.map(Arrow.newArrow().withTip(Potion.newStrongPoison()).asItemStack()));
    mob.setSlot(EntityEquipmentSlot.MAINHAND, ItemWeapon.getBow(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level)));

    Arrays.stream(ArmourType.values()).forEach(armourType -> {
      EntityEquipmentSlot slot = Slot.getSlot(armourType.asSlot());
      ItemStack item = ItemArmour.create(armourType, Quality.WOOD, Colors.PALE_LIME_GREEN);
      Enchant.enchantItem(rand, item, 20);
      mob.setSlot(slot, item);
    });
  }

}
