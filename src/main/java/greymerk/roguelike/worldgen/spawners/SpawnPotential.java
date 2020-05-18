package greymerk.roguelike.worldgen.spawners;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.Equipment;

import static greymerk.roguelike.treasure.loot.Equipment.AXE;
import static greymerk.roguelike.treasure.loot.Equipment.BOW;
import static greymerk.roguelike.treasure.loot.Equipment.CHEST;
import static greymerk.roguelike.treasure.loot.Equipment.FEET;
import static greymerk.roguelike.treasure.loot.Equipment.HELMET;
import static greymerk.roguelike.treasure.loot.Equipment.LEGS;
import static greymerk.roguelike.treasure.loot.Equipment.PICK;
import static greymerk.roguelike.treasure.loot.Equipment.SHOVEL;
import static greymerk.roguelike.treasure.loot.Equipment.SWORD;
import static greymerk.roguelike.treasure.loot.Equipment.getName;
import static greymerk.roguelike.treasure.loot.Quality.getArmourQuality;
import static greymerk.roguelike.treasure.loot.Quality.getToolQuality;
import static greymerk.roguelike.treasure.loot.Quality.getWeaponQuality;
import static greymerk.roguelike.worldgen.spawners.MobType.SKELETON;
import static greymerk.roguelike.worldgen.spawners.MobType.ZOMBIE;
import static java.util.stream.IntStream.range;

public class SpawnPotential {

  private String name;
  private int weight;
  private boolean equip;
  private NBTTagCompound entityNbt;

  public SpawnPotential(String name, boolean equip, int weight, NBTTagCompound entityNbt) {
    this.name = name;
    this.equip = equip;
    this.weight = weight;
    this.entityNbt = entityNbt;
  }

  public NBTTagList getSpawnPotentials(Random random, int level) {
    NBTTagList potentials = new NBTTagList();
    if (ZOMBIE.getName().equals(name)) {
      range(0, 24).forEach(i -> potentials.appendTag(buildSpawnPotentialNbt(createEquippedEntityNbt(random, level))));
    } else if (SKELETON.getName().equals(name)) {
      range(0, 12).forEach(i -> potentials.appendTag(buildSpawnPotentialNbt(createEquippedEntityNbt(random, level))));
    } else {
      potentials.appendTag(createSpawnPotentialNbt(level));
    }
    return potentials;
  }

  private NBTTagCompound createSpawnPotentialNbt(int level) {
    NBTTagCompound entityNbt = createEntityNbt(level);
    return buildSpawnPotentialNbt(entityNbt);
  }

  private NBTTagCompound createEquippedEntityNbt(Random random, int level) {
    NBTTagCompound entityNbt = createEntityNbt(level);
    equipHands(entityNbt, random, level);
    equipArmour(entityNbt, random, level);
    return entityNbt;
  }

  private NBTTagCompound createEntityNbt(int level) {
    // This copy of entityNbt is non-obvious but important. It's so that there can be a variety of spawn potentials for Zombies/skeletons/whatever
    NBTTagCompound entityNbt = this.entityNbt.copy();
    setRoguelikeNbtData(level, name, entityNbt);
    return entityNbt;
  }

  private void equipHands(NBTTagCompound entityNbt, Random random, int level) {
    String mainHand = getMainhand(random, level);
    String offHand = "minecraft:shield";
    equipHands(entityNbt, mainHand, offHand);
  }

  private String getMainhand(Random random, int level) {
    return random.nextBoolean()
        ? random.nextBoolean()
        ? getName(chooseRandomWeapon(random), getWeaponQuality(random, level))
        : getName(chooseRandomTool(random), getToolQuality(random, level))
        : null;
  }

  private Equipment chooseRandomWeapon(Random random) {
    return random.nextBoolean()
        ? SWORD
        : BOW;
  }

  private Equipment chooseRandomTool(Random random) {
    return random.nextBoolean()
        ? random.nextBoolean()
        ? PICK
        : SHOVEL
        : AXE;
  }

  private NBTTagCompound buildSpawnPotentialNbt(NBTTagCompound entityNbt) {
    NBTTagCompound spawnPotentialNbt = new NBTTagCompound();
    spawnPotentialNbt.setTag("Entity", entityNbt);
    spawnPotentialNbt.setInteger("Weight", weight);
    return spawnPotentialNbt;
  }

  private void equipHands(NBTTagCompound entityNbt, String mainHand, String offhand) {
    NBTTagList hands = new NBTTagList();
    hands.appendTag(getItem(mainHand));
    hands.appendTag(getItem(offhand));
    entityNbt.setTag("HandItems", hands);
  }

  private void equipArmour(NBTTagCompound entityNbt, Random rand, int level) {
    NBTTagList armour = new NBTTagList();
    armour.appendTag(getItem(getName(FEET, getArmourQuality(rand, level))));
    armour.appendTag(getItem(getName(LEGS, getArmourQuality(rand, level))));
    armour.appendTag(getItem(getName(CHEST, getArmourQuality(rand, level))));
    armour.appendTag(getItem(getName(HELMET, getArmourQuality(rand, level))));
    entityNbt.setTag("ArmorItems", armour);
  }

  private NBTTagCompound getItem(String itemName) {
    NBTTagCompound item = new NBTTagCompound();
    if (itemName == null) {
      return item;
    }
    item.setString("id", itemName);
    item.setInteger("Count", 1);
    return item;
  }

  private void setRoguelikeNbtData(int level, String type, NBTTagCompound entityNbt) {
    entityNbt.setString("id", type);

    if (!(RogueConfig.getBoolean(RogueConfig.ROGUESPAWNERS)
        && equip)) {
      return;
    }

    NBTTagCompound buff = new NBTTagCompound();
    buff.setByte("Id", (byte) 4);
    buff.setByte("Amplifier", (byte) level);
    buff.setInteger("Duration", 10);
    buff.setByte("Ambient", (byte) 0);

    NBTTagList activeEffects = new NBTTagList();
    activeEffects.appendTag(buff);

    entityNbt.setTag("ActiveEffects", activeEffects);
  }

}
