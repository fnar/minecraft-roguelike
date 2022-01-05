package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class ProfilePigZombie implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    RldItemStack weapon = WeaponLootItem.getSword(random, level, difficulty);
    mob.equipMainhand(weapon);
    mob.equipShield(random);
    mob.equipArmor(random, level, Color.random(), difficulty);
    return mob;
  }

}
