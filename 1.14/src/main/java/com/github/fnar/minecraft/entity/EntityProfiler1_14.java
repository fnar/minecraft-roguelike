package com.github.fnar.minecraft.entity;

import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_14;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfileType;

public class EntityProfiler1_14 {

  public static LivingEntity applyProfile(LivingEntity oldEntity, int level, Random random, int difficulty) {
    Mob mob = applyProfile(oldEntity, level, difficulty, random);

    if (mob == null) {
      return null;
    }

    LivingEntity newEntity = createNewInstance(mob.getMobType(), oldEntity.getEntityWorld());

    if (mob.getName() != null) {
      newEntity.setCustomName(new StringTextComponent(mob.getName()));
      newEntity.setCustomNameVisible(true);
    }

    if (newEntity instanceof ZombieEntity) {
      ((ZombieEntity) newEntity).setChild(oldEntity.isChild() || mob.isChild());
    }

    if (mob.isEquippable()) {
      for (EquipmentSlotType slot : EquipmentSlotType.values()) {
        ItemStack toTrade = oldEntity.getItemStackFromSlot(slot);
        newEntity.setItemStackToSlot(slot, toTrade);
      }

      mob.getItems().forEach((slot, rldItemStack) -> {
        EquipmentSlotType equipmentSlot = new SlotMapper1_14().map(slot);
        ItemStack item = new ItemMapper1_14().map(rldItemStack);
        newEntity.setItemStackToSlot(equipmentSlot, item);
      });
    }

    return newEntity;
  }

  private static LivingEntity createNewInstance(MobType mobType, World world) {
    switch (mobType) {
      case BAT:
        return EntityType.BAT.create(world);
      case BLAZE:
        return EntityType.BLAZE.create(world);
      case CAVESPIDER:
        return EntityType.CAVE_SPIDER.create(world);
      case CREEPER:
        return EntityType.CREEPER.create(world);
      case ELDER_GUARDIAN:
        return EntityType.ELDER_GUARDIAN.create(world);
      case ENDERMAN:
        return EntityType.ENDERMAN.create(world);
      case ENDERMITE:
        return EntityType.ENDERMITE.create(world);
      case ENDER_DRAGON:
        return EntityType.ENDER_DRAGON.create(world);
      case EVOKER:
        return EntityType.EVOKER.create(world);
      case GHAST:
        return EntityType.GHAST.create(world);
      case GUARDIAN:
        return EntityType.GUARDIAN.create(world);
      case HUSK:
        return EntityType.HUSK.create(world);
      case ILLUSIONER:
        return EntityType.ILLUSIONER.create(world);
      case MAGMA_CUBE:
        return EntityType.MAGMA_CUBE.create(world);
      case SHULKER:
        return EntityType.SHULKER.create(world);
      case SILVERFISH:
        return EntityType.SILVERFISH.create(world);
      case SKELETON:
        return EntityType.SKELETON.create(world);
      case SLIME:
        return EntityType.SLIME.create(world);
      case SPIDER:
        return EntityType.SPIDER.create(world);
      case STRAY:
        return EntityType.STRAY.create(world);
      case VEX:
        return EntityType.VEX.create(world);
      case VINDICATOR:
        return EntityType.VINDICATOR.create(world);
      case WITCH:
        return EntityType.WITCH.create(world);
      case WITHER:
        return EntityType.WITHER.create(world);
      case WITHER_SKELETON:
        return EntityType.WITHER_SKELETON.create(world);
      default:
      case ZOMBIE:
        return new ZombieEntity(world);
      case ZOMBIE_PIGMAN:
        return EntityType.ZOMBIE_PIGMAN.create(world);
    }
  }

  private static Mob applyProfile(LivingEntity entityLiving, int level, int difficulty, Random random) {
    if (entityLiving instanceof BlazeEntity) {
      return new Mob().withMobType(MobType.BLAZE);
    }
    if (entityLiving instanceof CaveSpiderEntity) {
      return new Mob().withMobType(MobType.CAVESPIDER);
    }
    if (entityLiving instanceof CreeperEntity) {
      return new Mob().withMobType(MobType.CREEPER);
    }
    if (entityLiving instanceof EndermanEntity) {
      return new Mob().withMobType(MobType.ENDERMAN);
    }
    if (entityLiving instanceof EndermiteEntity) {
      return new Mob().withMobType(MobType.ENDERMITE);
    }
    if (entityLiving instanceof EvokerEntity) {
      return new Mob().apply(MonsterProfileType.EVOKER, level, difficulty, random);
    }
    if (entityLiving instanceof GhastEntity) {
      return new Mob().withMobType(MobType.GHAST);
    }
    if (entityLiving instanceof GuardianEntity) {
      return new Mob().withMobType(MobType.GUARDIAN);
    }
    if (entityLiving instanceof HuskEntity) {
      return new Mob().apply(MonsterProfileType.HUSK, level, difficulty, random);
    }
    if (entityLiving instanceof IllusionerEntity) {
      return new Mob().withMobType(MobType.ILLUSIONER);
    }
    if (entityLiving instanceof MagmaCubeEntity) {
      return new Mob().withMobType(MobType.MAGMA_CUBE);
    }
    if (entityLiving instanceof ShulkerEntity) {
      return new Mob().withMobType(MobType.SHULKER);
    }
    if (entityLiving instanceof SilverfishEntity) {
      return new Mob().withMobType(MobType.SILVERFISH);
    }
    if (entityLiving instanceof SkeletonEntity) {
      return new Mob().apply(MonsterProfileType.SKELETON, level, difficulty, random);
    }
    if (entityLiving instanceof SlimeEntity) {
      return new Mob().withMobType(MobType.SLIME);
    }
    if (entityLiving instanceof SpiderEntity) {
      return new Mob().withMobType(MobType.SPIDER);
    }
    if (entityLiving instanceof StrayEntity) {
      return new Mob().withMobType(MobType.STRAY);
    }
    if (entityLiving instanceof VexEntity) {
      return new Mob().withMobType(MobType.VEX);
    }
    if (entityLiving instanceof VindicatorEntity) {
      return new Mob().apply(MonsterProfileType.VINDICATOR, level, difficulty, random);
    }
    if (entityLiving instanceof WitchEntity) {
      return new Mob().apply(MonsterProfileType.WITCH, level, difficulty, random);
    }
    if (entityLiving instanceof WitherSkeletonEntity) {
      return new Mob().apply(MonsterProfileType.WITHER_SKELETON, level, difficulty, random);
    }
    if (entityLiving instanceof ZombiePigmanEntity) {
      return new Mob().apply(MonsterProfileType.PIG_ZOMBIE, level, difficulty, random);
    }
    if (entityLiving instanceof ZombieVillagerEntity) {
      return new Mob().apply(MonsterProfileType.ZOMBIE_VILLAGER, level, difficulty, random);
    }
    if (entityLiving instanceof ZombieEntity) {
      return new Mob().apply(MonsterProfileType.ZOMBIE, level, difficulty, random);
    }
    return null;
  }

}
