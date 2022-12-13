package greymerk.roguelike;

import com.google.common.primitives.Ints;

import com.github.fnar.minecraft.EffectType;
import com.github.fnar.util.ReportThisIssueException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.Random;

import greymerk.roguelike.monster.EntityMapper1_12;

public class EntityJoinWorld1_12 {

  @SubscribeEvent
  public void OnEntityJoinWorld(EntityJoinWorldEvent event) {

    try {
      equipMobIfSpawnedByRoguelikeSpawner(event);
    } catch (Exception exception) {
      new ReportThisIssueException(exception).printStackTrace();
    }
  }

  private void equipMobIfSpawnedByRoguelikeSpawner(EntityJoinWorldEvent event) {
    World world = event.getWorld();
    if (world.isRemote) {
      return;
    }

    Entity entity = event.getEntity();

    // slimes do not extend EntityMob for some reason
    if (!(entity instanceof EntityMob || entity instanceof EntitySlime)) {
      return;
    }

    EntityLiving entityLiving = (EntityLiving) entity;
    if (entity.isDead) {
      event.setCanceled(true);
      return;
    }

    Collection<PotionEffect> effects = entityLiving.getActivePotionEffects();
    for (PotionEffect effect : effects) {
      if (!isMobFromRoguelikeSpawner(effect)) {
        continue;
      }

      int level = Ints.constrainToRange(effect.getAmplifier(), 0, 4);

      Random random = world.rand;
      int difficulty = world.getDifficulty().ordinal();

      Entity map = EntityMapper1_12.map(entityLiving, level, random, difficulty);
      if (map == null) {
        continue;
      }
      entityLiving.world.removeEntity(entityLiving);
      entityLiving.world.spawnEntity(map);
    }
  }

  private boolean isMobFromRoguelikeSpawner(PotionEffect buff) {
    return Potion.getIdFromPotion(buff.getPotion()) == EffectType.FATIGUE.getEffectID();
  }
}
