package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class ProfileArcher implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    mob.equipMainhand(WeaponLootItem.getBow(rand, level, difficulty));

    boolean hasPoisonTippedArrows = rand.nextInt(10) == 0;
    if (hasPoisonTippedArrows) {
      mob.equipArrows(Arrow.newRandomHarmful(rand));
    }
    mob.equipArmor(rand, level, Color.random(), difficulty);
    return mob;
  }

}
