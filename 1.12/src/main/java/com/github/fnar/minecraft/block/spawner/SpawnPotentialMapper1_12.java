package com.github.fnar.minecraft.block.spawner;

import com.github.fnar.minecraft.EffectType;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_12;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.monster.Mob;

import static java.util.stream.IntStream.range;

public class SpawnPotentialMapper1_12 {

  public static NBTTagList mapToNbt(List<SpawnPotential> spawnerPotentials, Random random, int level) {
    NBTTagList potentialsNbtTagList = new NBTTagList();
    spawnerPotentials.stream()
        .map(potential -> mapToNbt(potential, random, level))
        .forEach(potentials -> potentials.forEach(potentialsNbtTagList::appendTag));
    return potentialsNbtTagList;
  }

  private static NBTTagList mapToNbt(SpawnPotential spawnPotential, Random random, int level) {
    NBTTagList potentials = new NBTTagList();
      range(0, 12)
          .mapToObj(i -> spawnPotential.generateMob(random, level))
          .map(mob -> mapToNbt(spawnPotential, level, mob))
          .forEach(potentials::appendTag);
    return potentials;
  }

  private static NBTTagCompound mapToNbt(SpawnPotential spawnPotential, int level, Mob mob) {
    NBTTagCompound entityNbt = createEntityNbt(level, jsonToNbt(spawnPotential.getNbt()), spawnPotential.getName());
    addHandTags(entityNbt, mob.getMainhand(), mob.getOffhand());
    addArmourTags(entityNbt, mob.getBoots(), mob.getLeggings(), mob.getChestplate(), mob.getHelmet());
    return buildSpawnPotentialNbt(entityNbt, spawnPotential.getWeight());
  }

  private static NBTTagCompound createEntityNbt(int level, NBTTagCompound entityNbt, String entityName) {
    // This copy of entityNbt is non-obvious but important. It's so that there can be a variety of spawn potentials for Zombies/skeletons/whatever
    NBTTagCompound entityNbtCopy = entityNbt.copy();
    addRoguelikeNbtData(entityNbtCopy, entityName, level);
    return entityNbtCopy;
  }

  private static void addHandTags(NBTTagCompound entityNbt, RldItem mainhand, RldItem offhand) {
    NBTTagList hands = new NBTTagList();
    appendItemTag(hands, mainhand);
    appendItemTag(hands, offhand);
    entityNbt.setTag("HandItems", hands);
  }

  private static NBTTagCompound mapItemToTag(RldItem mainhand) {
    if (mainhand == null) {
      return null;
    }
    try {
      String itemName = new ItemMapper1_12().map(mainhand.asStack()).getItem().getRegistryName().toString();
      NBTTagCompound item = new NBTTagCompound();
      item.setString("id", itemName);
      item.setInteger("Count", 1);
      return item;
    } catch (NullPointerException ignored) {
      return null;
    }
  }

  private static NBTTagCompound buildSpawnPotentialNbt(NBTTagCompound entityNbt, int weight) {
    NBTTagCompound spawnPotentialNbt = new NBTTagCompound();
    spawnPotentialNbt.setTag("Entity", entityNbt);
    spawnPotentialNbt.setInteger("Weight", weight);
    return spawnPotentialNbt;
  }

  private static void addArmourTags(NBTTagCompound entityNbt, RldItem boots, RldItem leggings, RldItem chestplate, RldItem helmet) {
    NBTTagList armour = new NBTTagList();
    // Turns out this order is important.
    appendItemTag(armour, boots);
    appendItemTag(armour, leggings);
    appendItemTag(armour, chestplate);
    appendItemTag(armour, helmet);
    entityNbt.setTag("ArmorItems", armour);
  }

  private static void appendItemTag(NBTTagList parent, RldItem item) {
    NBTTagCompound itemTag = mapItemToTag(item);
    if (itemTag == null) {
      return;
    }
    parent.appendTag(itemTag);
  }

  private static void addRoguelikeNbtData(NBTTagCompound entityNbt, String type, int level) {
    entityNbt.setString("id", type);
    tagEntityAsFromRoguelikeSpawner(entityNbt, level);
    setLootingRateTags(entityNbt);
  }

  private static void tagEntityAsFromRoguelikeSpawner(NBTTagCompound entityNbt, int level) {
    // This tags the entity as a "roguelike" entity, by giving the entity mining fatigue.
    // Later, a check for mining fatigue determines if it should equip this entity.
    NBTTagList activeEffects = new NBTTagList();
    activeEffects.appendTag(getMiningFatigueBuff(level));
    entityNbt.setTag("ActiveEffects", activeEffects);
  }

  private static NBTTagCompound getMiningFatigueBuff(int level) {
    NBTTagCompound buff = new NBTTagCompound();
    buff.setByte("Id", (byte) EffectType.FATIGUE.getEffectID());
    buff.setByte("Amplifier", (byte) level);
    buff.setInteger("Duration", 10);
    buff.setByte("Ambient", (byte) 0);
    return buff;
  }

  private static void setLootingRateTags(NBTTagCompound entityNbt) {
    float mobItemsDropChance = (float) RogueConfig.MOBS_ITEMS_DROP_CHANCE.getDouble();

    // order matters
    NBTTagList handDropChances = new NBTTagList();
    NBTTagFloat mainHandDropChance = new NBTTagFloat(mobItemsDropChance);
    NBTTagFloat offHandDropChance = new NBTTagFloat(mobItemsDropChance);
    handDropChances.appendTag(mainHandDropChance);
    handDropChances.appendTag(offHandDropChance);
    entityNbt.setTag("HandDropChances", handDropChances);

    // order matters
    NBTTagList armorDropChances = new NBTTagList();
    NBTTagFloat feetDropChance = new NBTTagFloat(mobItemsDropChance);
    NBTTagFloat legsDropChance = new NBTTagFloat(mobItemsDropChance);
    NBTTagFloat chestDropChance = new NBTTagFloat(mobItemsDropChance);
    NBTTagFloat headDropChance = new NBTTagFloat(mobItemsDropChance);
    armorDropChances.appendTag(feetDropChance);
    armorDropChances.appendTag(legsDropChance);
    armorDropChances.appendTag(chestDropChance);
    armorDropChances.appendTag(headDropChance);
    entityNbt.setTag("ArmorDropChances", armorDropChances);
  }

  private static NBTTagCompound jsonToNbt(String entityNbt) {
    try {
      return JsonToNBT.getTagFromJson(entityNbt);
    } catch (NBTException e) {
      return new NBTTagCompound();
    }
  }
}
