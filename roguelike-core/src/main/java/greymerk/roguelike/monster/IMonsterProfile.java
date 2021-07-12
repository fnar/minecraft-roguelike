package greymerk.roguelike.monster;

import net.minecraft.world.World;

import java.util.Random;

public interface IMonsterProfile {

  void equip(World world, Random rand, int level, IEntity mob);

}
