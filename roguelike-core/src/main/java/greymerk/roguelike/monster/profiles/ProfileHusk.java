package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.ToolLootItem;

public class ProfileHusk implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    mob.equipMainhand(ToolLootItem.get(random, level, difficulty));
    mob.equipShield(random);
    mob.equipArmor(random, level, Color.random(), difficulty);
    return mob;
  }
}
