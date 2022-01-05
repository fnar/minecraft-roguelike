package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class ProfileSwordsman implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    RldItemStack weapon = random.nextInt(20) == 0
        ? ItemNovelty.valandrahsKiss()
        : WeaponLootItem.getSword(random, level, difficulty);

    mob.equipMainhand(weapon);
    mob.equipShield(random);
    mob.equipArmor(random, level, Color.random(), difficulty);
    return mob;
  }

}
