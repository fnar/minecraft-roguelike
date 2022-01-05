package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.Mob;

public class ProfileWitch implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    return mob;
  }

}
