package greymerk.roguelike.monster;

import java.util.Random;

public interface MonsterProfile {

  Mob apply(Mob mob, int level, int difficulty, Random rand);

}
