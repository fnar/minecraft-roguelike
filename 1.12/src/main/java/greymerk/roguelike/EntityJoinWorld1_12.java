package greymerk.roguelike;

import com.google.common.primitives.Ints;

import com.github.fnar.minecraft.EffectType;
import com.github.fnar.util.Pair;
import com.github.fnar.util.ReportThisIssueException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.monster.EntityProfiler1_12;

public class EntityJoinWorld1_12 {

  @SubscribeEvent
  public void OnEntityJoinWorld(EntityJoinWorldEvent event) {

    try {
      equipMobIfSpawnedByRoguelikeSpawner(event);
    } catch (Exception exception) {
      new ReportThisIssueException(exception).printStackTrace();
    }
  }

  private static void equipMobIfSpawnedByRoguelikeSpawner(EntityJoinWorldEvent event) {
    World world = event.getWorld();
    if (world.isRemote) {
      return;
    }

    Entity entity = event.getEntity();

    // slimes do not extend EntityMob for some reason
    if (!(entity instanceof EntityMob || entity instanceof EntitySlime)) {
      return;
    }

    if (entity.isDead) {
      event.setCanceled(true);
      return;
    }

    EntityLiving entityLiving = (EntityLiving) entity;

    if (!isMobFromRoguelikeSpawner(entityLiving)) {
      return;
    }

    handleDespawning(entityLiving);

    if (RogueConfig.MOBS_PROFILES_ENABLED.getBoolean()) {
      applyMobProfile(world, entityLiving);
    }
  }

  private static boolean isMobFromRoguelikeSpawner(EntityLiving entity) {
    return entity.getActivePotionEffects().stream().anyMatch(EntityJoinWorld1_12::isRoguelikeTag);
  }

  private static void applyMobProfile(World world, EntityLiving entity) {
    int level = Ints.constrainToRange(getRoguelikeLevel(entity), 0, 4);

    int difficulty = world.getDifficulty().ordinal();

    EntityLiving newEntity = EntityProfiler1_12.applyProfile(entity, level, world.rand, difficulty);
    if (newEntity == null) {
      return;
    }

    newEntity.copyLocationAndAnglesFrom(entity);
    copyTags(entity, newEntity);
    Arrays.stream(EntityEquipmentSlot.values()).forEach(value -> newEntity.setDropChance(value, (float) RogueConfig.MOBS_ITEMS_DROP_CHANCE.getDouble()));

    // Mob type might be changed by this mod, so it's important to respawn
    entity.world.removeEntity(entity);
    newEntity.world.spawnEntity(newEntity);
  }

  private static int getRoguelikeLevel(EntityLiving entity) {
    return entity.getActivePotionEffects().stream()
        .filter(EntityJoinWorld1_12::isRoguelikeTag).findFirst()
        .map(PotionEffect::getAmplifier)
        .orElse(0);
  }

  private static boolean isRoguelikeTag(PotionEffect buff) {
    return Potion.getIdFromPotion(buff.getPotion()) == EffectType.FATIGUE.getEffectID();
  }

  private static void copyTags(EntityLiving from, EntityLiving to) {
    // have to duplicate logic for despawning since this tag doesn't carry over ?? Forge, why?
    handleDespawning(to);
    from.getTags().forEach(to::addTag);
    copyEntityData(from, to);
  }

  private static void handleDespawning(EntityLiving entityLiving) {
    if (!RogueConfig.MOBS_DESPAWN_ENABLED.getBoolean()) {
      entityLiving.enablePersistence();
    }
  }

  private static void copyEntityData(EntityLiving from, EntityLiving to) {
    from.getEntityData().getKeySet().stream()
        .map(tag -> new Pair<>(tag, from.getEntityData().getTag(tag)))
        .forEach(tagPair -> to.getEntityData().setTag(tagPair.getKey(), tagPair.getValue()));
  }
}
