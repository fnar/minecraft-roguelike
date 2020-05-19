package greymerk.roguelike.monster;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.profiles.ProfileArcher;
import greymerk.roguelike.monster.profiles.ProfileAshlea;
import greymerk.roguelike.monster.profiles.ProfileBaby;
import greymerk.roguelike.monster.profiles.ProfileEvoker;
import greymerk.roguelike.monster.profiles.ProfileHusk;
import greymerk.roguelike.monster.profiles.ProfileJohnny;
import greymerk.roguelike.monster.profiles.ProfileMagicArcher;
import greymerk.roguelike.monster.profiles.ProfilePigman;
import greymerk.roguelike.monster.profiles.ProfilePoisonArcher;
import greymerk.roguelike.monster.profiles.ProfileRleahy;
import greymerk.roguelike.monster.profiles.ProfileSkeleton;
import greymerk.roguelike.monster.profiles.ProfileSwordsman;
import greymerk.roguelike.monster.profiles.ProfileTallMob;
import greymerk.roguelike.monster.profiles.ProfileVillager;
import greymerk.roguelike.monster.profiles.ProfileVindicator;
import greymerk.roguelike.monster.profiles.ProfileWitch;
import greymerk.roguelike.monster.profiles.ProfileWither;
import greymerk.roguelike.monster.profiles.ProfileZombie;

public enum MonsterProfile {
  TALLMOB(new ProfileTallMob()),
  ZOMBIE(new ProfileZombie()),
  PIGMAN(new ProfilePigman()),
  SKELETON(new ProfileSkeleton()),
  VILLAGER(new ProfileVillager()),
  HUSK(new ProfileHusk()),
  BABY(new ProfileBaby()),
  ASHLEA(new ProfileAshlea()),
  RLEAHY(new ProfileRleahy()),
  ARCHER(new ProfileArcher()),
  WITHER(new ProfileWither()),
  POISONARCHER(new ProfilePoisonArcher()),
  MAGICARCHER(new ProfileMagicArcher()),
  SWORDSMAN(new ProfileSwordsman()),
  EVOKER(new ProfileEvoker()),
  VINDICATOR(new ProfileVindicator()),
  WITCH(new ProfileWitch()),
  JOHNNY(new ProfileJohnny()),
  ;

  private final IMonsterProfile monsterProfile;

  MonsterProfile(IMonsterProfile monsterProfile) {
    this.monsterProfile = monsterProfile;
  }

  public IMonsterProfile getMonsterProfile() {
    return monsterProfile;
  }

  public static void equip(World world, Random rand, int level, IEntity mob) {
    if (mob.instance(EntityZombie.class)) {
      ZOMBIE.getMonsterProfile().addEquipment(world, rand, level, mob);
    } else if (mob.instance(EntitySkeleton.class)) {
      SKELETON.getMonsterProfile().addEquipment(world, rand, level, mob);
    }
  }

}
