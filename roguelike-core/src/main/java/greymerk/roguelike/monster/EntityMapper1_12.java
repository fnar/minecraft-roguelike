 package greymerk.roguelike.monster;

import com.github.fnar.minecraft.entity.SlotMapper1_12;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_12;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class EntityMapper1_12 {

  public static Entity map(EntityLiving entityLiving, int level, Random random, int difficulty) {
    Mob mob = applyProfile(entityLiving, level, difficulty, random);

    if (mob == null) {
      return null;
    }
    EntityLiving newMob;
    try {
      newMob = entityLiving.getClass().getConstructor(World.class).newInstance(entityLiving.world);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      return null;
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      return null;
    } catch (InstantiationException e) {
      e.printStackTrace();
      return null;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }

    newMob.copyLocationAndAnglesFrom(entityLiving);

    mob.getItems().forEach((key, value) -> {
      EntityEquipmentSlot slot = new SlotMapper1_12().map(key);
      ItemStack item = new ItemMapper1_12().map(value);
      newMob.setItemStackToSlot(slot, item);
    });

    if (newMob instanceof EntityZombie) {
      ((EntityZombie) newMob).setChild(entityLiving.isChild() || mob.isChild());
    }

    for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
      ItemStack toTrade = entityLiving.getItemStackFromSlot(slot);
      newMob.setItemStackToSlot(slot, toTrade);
    }

    entityLiving.world.removeEntity(entityLiving);
    newMob.world.spawnEntity(newMob);

    if (mob.getName() != null) {
      newMob.setCustomNameTag(mob.getName());
      newMob.setAlwaysRenderNameTag(true);
    }

    return newMob;
  }

  private static Mob applyProfile(EntityLiving entityLiving, int level, int difficulty, Random random) {
    if (entityLiving instanceof EntityCreeper) {
      return new Mob();
    }
    if (entityLiving instanceof EntityEvoker) {
      return new Mob().apply(MonsterProfileType.EVOKER, level, difficulty, random);
    }
    if (entityLiving instanceof EntityHusk) {
      return new Mob().apply(MonsterProfileType.HUSK, level, difficulty, random);
    }
    if (entityLiving instanceof EntitySkeleton) {
      return new Mob().apply(MonsterProfileType.SKELETON, level, difficulty, random);
    }
    if (entityLiving instanceof EntityStray) {
      return new Mob();
    }
    if (entityLiving instanceof EntitySpider) {
      return new Mob();
    }
    if (entityLiving instanceof EntityVindicator) {
      return new Mob().apply(MonsterProfileType.VINDICATOR, level, difficulty, random);
    }
    if (entityLiving instanceof EntityWitch) {
      return new Mob().apply(MonsterProfileType.WITCH, level, difficulty, random);
    }
    if (entityLiving instanceof EntityWitherSkeleton) {
      return new Mob().apply(MonsterProfileType.WITHER_SKELETON, level, difficulty, random);
    }
    if (entityLiving instanceof EntityPigZombie) {
      return new Mob().apply(MonsterProfileType.PIG_ZOMBIE, level, difficulty, random);
    }
    if (entityLiving instanceof EntityZombieVillager) {
      return new Mob().apply(MonsterProfileType.ZOMBIE_VILLAGER, level, difficulty, random);
    }
    if (entityLiving instanceof EntityZombie) {
      return new Mob().apply(MonsterProfileType.ZOMBIE, level, difficulty, random);
    }
    return new Mob();
  }

}
