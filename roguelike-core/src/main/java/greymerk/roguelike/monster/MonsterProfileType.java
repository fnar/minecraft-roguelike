package greymerk.roguelike.monster;

import java.util.Random;

import greymerk.roguelike.monster.profiles.ProfileArcher;
import greymerk.roguelike.monster.profiles.ProfileAshlea;
import greymerk.roguelike.monster.profiles.ProfileBaby;
import greymerk.roguelike.monster.profiles.ProfileEvoker;
import greymerk.roguelike.monster.profiles.ProfileHusk;
import greymerk.roguelike.monster.profiles.ProfileJohnny;
import greymerk.roguelike.monster.profiles.ProfileMagicArcher;
import greymerk.roguelike.monster.profiles.ProfilePigZombie;
import greymerk.roguelike.monster.profiles.ProfilePoisonArcher;
import greymerk.roguelike.monster.profiles.ProfileRleahy;
import greymerk.roguelike.monster.profiles.ProfileSkeleton;
import greymerk.roguelike.monster.profiles.ProfileSwordsman;
import greymerk.roguelike.monster.profiles.ProfileZombieVillager;
import greymerk.roguelike.monster.profiles.ProfileVindicator;
import greymerk.roguelike.monster.profiles.ProfileWitch;
import greymerk.roguelike.monster.profiles.ProfileWitherSkeleton;
import greymerk.roguelike.monster.profiles.ProfileZombie;

public enum MonsterProfileType {

  ARCHER(new ProfileArcher()),
  ASHLEA(new ProfileAshlea()),
  BABY(new ProfileBaby()),
  EVOKER(new ProfileEvoker()),
  HUSK(new ProfileHusk()),
  JOHNNY(new ProfileJohnny()),
  MAGIC_ARCHER(new ProfileMagicArcher()),
  PIG_ZOMBIE(new ProfilePigZombie()),
  POISON_ARCHER(new ProfilePoisonArcher()),
  RLEAHY(new ProfileRleahy()),
  SKELETON(new ProfileSkeleton()),
  SWORDSMAN(new ProfileSwordsman()),
  VINDICATOR(new ProfileVindicator()),
  WITCH(new ProfileWitch()),
  WITHER_SKELETON(new ProfileWitherSkeleton()),
  ZOMBIE(new ProfileZombie()),
  ZOMBIE_VILLAGER(new ProfileZombieVillager()),
  ;

  private final MonsterProfile monsterProfile;

  MonsterProfileType(MonsterProfile monsterProfile) {
    this.monsterProfile = monsterProfile;
  }

  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    return monsterProfile.apply(mob, level, difficulty, rand);
  }

}
