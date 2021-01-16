package greymerk.roguelike.monster.profiles;

import com.github.srwaggon.minecraft.item.Arrow;
import com.github.srwaggon.minecraft.item.ItemMapper1_12;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public class ProfileMagicArcher implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random random, int level, IEntity mob) {

    mob.setMobClass(MobType.STRAY, false);

    mob.setSlot(EntityEquipmentSlot.OFFHAND, ItemMapper1_12.map(Arrow.newRandomHarmful(random).asItemStack()));
    mob.setSlot(EntityEquipmentSlot.MAINHAND, ItemWeapon.getBow(random, level, Enchant.canEnchant(world.getDifficulty(), random, level)));

    for (EntityEquipmentSlot slot : new EntityEquipmentSlot[]{
        EntityEquipmentSlot.HEAD,
        EntityEquipmentSlot.CHEST,
        EntityEquipmentSlot.LEGS,
        EntityEquipmentSlot.FEET
    }) {
      ItemStack item = ItemArmour.get(random, Slot.getSlot(slot), Quality.WOOD);
      Enchant.enchantItem(random, item, 20);
      ItemArmour.dyeArmor(item, 51, 0, 102);
      mob.setSlot(slot, item);
    }
  }

}
