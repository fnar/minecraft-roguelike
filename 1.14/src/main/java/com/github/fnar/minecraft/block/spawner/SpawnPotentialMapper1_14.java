package com.github.fnar.minecraft.block.spawner;

import com.github.fnar.minecraft.EffectType;
import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_14;
import com.github.fnar.roguelike.Roguelike;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.monster.Mob;

import static java.util.stream.IntStream.range;

public class SpawnPotentialMapper1_14 {

  public static ListNBT mapToNbt(List<SpawnPotential> spawnerPotentials, Random random, int level) {
    ListNBT potentialsNbtTagList = new ListNBT();
    spawnerPotentials.stream()
        .map(potential -> mapToNbt(potential, random, level))
        .forEach(potentials -> potentials.forEach(potentialsNbtTagList::add));
    return potentialsNbtTagList;
  }

  private static ListNBT mapToNbt(SpawnPotential spawnPotential, Random random, int level) {
    ListNBT potentials = new ListNBT();
    range(0, 12)
        .mapToObj(i -> spawnPotential.generateMob(random, level))
        .map(mob -> mapToNbt(spawnPotential, level, mob))
        .forEach(potentials::add);
    return potentials;
  }

  private static CompoundNBT mapToNbt(SpawnPotential spawnPotential, int level, Mob mob) {
    CompoundNBT entityNbt = createEntityNbt(level, jsonToNbt(spawnPotential.getNbt()), spawnPotential.getName());
    addHandTags(entityNbt, mob.getMainhand(), mob.getOffhand());
    addArmourTags(entityNbt, mob.getBoots(), mob.getLeggings(), mob.getChestplate(), mob.getHelmet());
    return buildSpawnPotentialNbt(entityNbt, spawnPotential.getWeight());
  }

  private static CompoundNBT createEntityNbt(int level, CompoundNBT entityNbt, String entityName) {
    // This copy of entityNbt is non-obvious but important. It's so that there can be a variety of spawn potentials for Zombies/skeletons/whatever
    CompoundNBT entityNbtCopy = entityNbt.copy();
    addRoguelikeNbtData(entityNbtCopy, entityName, level);
    return entityNbtCopy;
  }

  private static void addHandTags(CompoundNBT entityNbt, RldItem mainhand, RldItem offhand) {
    ListNBT hands = new ListNBT();
    appendItemTag(hands, mainhand);
    appendItemTag(hands, offhand);
    entityNbt.put("HandItems", hands);
  }

  private static CompoundNBT mapItemToTag(RldItem mainhand) {
    if (mainhand == null) {
      return null;
    }
    try {
      String itemName = new ItemMapper1_14().map(mainhand.asStack()).getItem().getRegistryName().toString();
      CompoundNBT item = new CompoundNBT();
      item.putString("id", itemName);
      item.putInt("Count", 1);
      return item;
    } catch (NullPointerException | CouldNotMapItemException e) {
      Roguelike.LOGGER.error(e);
      return null;
    }
  }

  private static CompoundNBT buildSpawnPotentialNbt(CompoundNBT entityNbt, int weight) {
    CompoundNBT spawnPotentialNbt = new CompoundNBT();
    spawnPotentialNbt.put("Entity", entityNbt);
    spawnPotentialNbt.putInt("Weight", weight);
    return spawnPotentialNbt;
  }

  private static void addArmourTags(CompoundNBT entityNbt, RldItem boots, RldItem leggings, RldItem chestplate, RldItem helmet) {
    ListNBT armour = new ListNBT();
    // Turns out this order is important.
    appendItemTag(armour, boots);
    appendItemTag(armour, leggings);
    appendItemTag(armour, chestplate);
    appendItemTag(armour, helmet);
    entityNbt.put("ArmorItems", armour);
  }

  private static void appendItemTag(ListNBT parent, RldItem item) {
    CompoundNBT itemTag = mapItemToTag(item);
    if (itemTag == null) {
      return;
    }
    parent.add(itemTag);
  }

  private static void addRoguelikeNbtData(CompoundNBT entityNbt, String type, int level) {
    entityNbt.putString("id", type);
    tagEntityAsFromRoguelikeSpawner(entityNbt, level);
    setLootingRateTags(entityNbt);
  }

  private static void tagEntityAsFromRoguelikeSpawner(CompoundNBT entityNbt, int level) {
    // This tags the entity as a "roguelike" entity, by giving the entity mining fatigue.
    // Later, a check for mining fatigue determines if it should equip this entity.
    ListNBT activeEffects = new ListNBT();
    activeEffects.add(getMiningFatigueBuff(level));
    entityNbt.put("ActiveEffects", activeEffects);
  }

  private static CompoundNBT getMiningFatigueBuff(int level) {
    CompoundNBT buff = new CompoundNBT();
    buff.putByte("Id", (byte) EffectType.FATIGUE.getEffectID());
    buff.putByte("Amplifier", (byte) level);
    buff.putInt("Duration", 10);
    buff.putByte("Ambient", (byte) 0);
    return buff;
  }

  private static void setLootingRateTags(CompoundNBT entityNbt) {
    float mobItemsDropChance = (float) RogueConfig.MOBS_ITEMS_DROP_CHANCE.getDouble();

    // order matters
    ListNBT handDropChances = new ListNBT();
    FloatNBT mainHandDropChance = new FloatNBT(mobItemsDropChance);
    FloatNBT offHandDropChance = new FloatNBT(mobItemsDropChance);
    handDropChances.add(mainHandDropChance);
    handDropChances.add(offHandDropChance);
    entityNbt.put("HandDropChances", handDropChances);

    // order matters
    ListNBT armorDropChances = new ListNBT();
    FloatNBT feetDropChance = new FloatNBT(mobItemsDropChance);
    FloatNBT legsDropChance = new FloatNBT(mobItemsDropChance);
    FloatNBT chestDropChance = new FloatNBT(mobItemsDropChance);
    FloatNBT headDropChance = new FloatNBT(mobItemsDropChance);
    armorDropChances.add(feetDropChance);
    armorDropChances.add(legsDropChance);
    armorDropChances.add(chestDropChance);
    armorDropChances.add(headDropChance);
    entityNbt.put("ArmorDropChances", armorDropChances);
  }

  private static CompoundNBT jsonToNbt(String entityNbt) {
    try {
      return JsonToNBT.getTagFromJson(entityNbt);
    } catch (CommandSyntaxException e) {
      return new CompoundNBT();
    }
  }

}
