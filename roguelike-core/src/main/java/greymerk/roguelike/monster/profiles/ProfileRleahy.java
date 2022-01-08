package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileRleahy implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    RldItemStack weapon = ItemNovelty.rleahianBattleSub();
    mob.equipMainhand(weapon);
    mob.equipShield(random);

    RldItemStack boots = ArmourType.BOOTS.asItem().withQuality(Quality.WOOD).withColor(Color.LEAD).asStack();
    mob.equip(Slot.FEET, boots);

    RldItemStack leggings = ArmourType.LEGGINGS.asItem().withQuality(Quality.WOOD).withColor(Color.PRUSSIAN_BLUE).asStack();
    mob.equip(Slot.LEGS, leggings);

    RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().withQuality(Quality.WOOD).withColor(Color.CLASSIC_ROSE).asStack();
    mob.equip(Slot.CHEST, chestplate);
    return mob;
  }
}
