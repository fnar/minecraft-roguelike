package greymerk.roguelike.monster;

import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

public interface IMonsterProfile {

  static boolean canEnchant(EnumDifficulty difficulty, Random rand, int level) {
    EnumDifficulty ensureDifficulty = Optional.ofNullable(difficulty).orElse(EnumDifficulty.NORMAL);

    switch (ensureDifficulty) {
      case PEACEFUL:
        return false;
      case EASY:
        return rand.nextInt(6) == 0;
      case NORMAL:
        return level >= 1 && rand.nextInt(4) == 0;
      case HARD:
        return rand.nextBoolean();
    }

    return false;
  }

  void equip(World world, Random rand, int level, IEntity mob);

}
