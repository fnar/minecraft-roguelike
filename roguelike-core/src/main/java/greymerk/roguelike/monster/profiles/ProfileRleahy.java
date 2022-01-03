package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Colors;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ArmourLootItem;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileRleahy implements IMonsterProfile {

  @Override
  public void equip(World world, Random random, int level, IEntity mob) {
    RldItemStack weapon = ItemNovelty.rleahianBattleSub();
    mob.equipMainhand(weapon);
    mob.equipShield(random);

    ItemStack boots = ArmourLootItem.create(ArmourType.BOOTS, Quality.WOOD, Colors.LEAD);
    mob.setSlot(EntityEquipmentSlot.FEET, boots);

    ItemStack leggings = ArmourLootItem.create(ArmourType.LEGGINGS, Quality.WOOD, Colors.PRUSSIAN_BLUE);
    mob.setSlot(EntityEquipmentSlot.LEGS, leggings);

    ItemStack chestplate = ArmourLootItem.create(ArmourType.CHESTPLATE, Quality.WOOD, Colors.CLASSIC_ROSE);
    mob.setSlot(EntityEquipmentSlot.CHEST, chestplate);
  }
}
