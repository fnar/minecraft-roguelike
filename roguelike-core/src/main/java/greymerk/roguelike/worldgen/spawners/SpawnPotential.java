package greymerk.roguelike.worldgen.spawners;

import com.github.fnar.minecraft.EffectType;
import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.minecraft.item.WeaponType;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;

import static greymerk.roguelike.treasure.loot.Equipment.CHEST;
import static greymerk.roguelike.treasure.loot.Equipment.FEET;
import static greymerk.roguelike.treasure.loot.Equipment.HELMET;
import static greymerk.roguelike.treasure.loot.Equipment.LEGS;
import static greymerk.roguelike.treasure.loot.provider.ItemArmour.rollArmourQuality;
import static greymerk.roguelike.treasure.loot.provider.ItemTool.rollToolQuality;
import static greymerk.roguelike.treasure.loot.provider.ItemWeapon.rollWeaponQuality;
import static java.util.stream.IntStream.range;

public class SpawnPotential {

  private final String name;
  private final int weight;
  private final boolean equip;
  private final NBTTagCompound entityNbt;

  public SpawnPotential(String name, boolean equip, int weight, NBTTagCompound entityNbt) {
    this.name = name;
    this.equip = equip;
    this.weight = weight;
    this.entityNbt = entityNbt;
  }

  public NBTTagList getSpawnPotentials(Random random, int level) {
    NBTTagList potentials = new NBTTagList();
    if (equip) {
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
    equipHands(entityNbt,
        getMainhand(random, level),
        getOffHand(random));
  }

  private String getMainhand(Random random, int level) {
    if (random.nextBoolean()) {
      return WeaponType.random(random).asEquipment().getMinecraftName(rollWeaponQuality(random, level));
    }
    if (random.nextBoolean()) {
      return ToolType.random(random).asEquipment().getMinecraftName(rollToolQuality(random, level));
    }
    return null;
  }

  private String getOffHand(Random random) {
    return random.nextBoolean()
        ? "minecraft:shield"
        : null;
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
    // Turns out this order is important.
    armour.appendTag(getItem(FEET.getMinecraftName(rollArmourQuality(rand, level))));
    armour.appendTag(getItem(LEGS.getMinecraftName(rollArmourQuality(rand, level))));
    armour.appendTag(getItem(CHEST.getMinecraftName(rollArmourQuality(rand, level))));
    armour.appendTag(getItem(HELMET.getMinecraftName(rollArmourQuality(rand, level))));
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

    if (areRoguelikeSpawnersEnabled()) {
      tagEntityAsFromRoguelikeSpawner(level, entityNbt);
    }

    setLootingRateTags(entityNbt);
  }

  private boolean areRoguelikeSpawnersEnabled() {
    return RogueConfig.ROGUESPAWNERS.getBoolean() && equip;
  }

  private void tagEntityAsFromRoguelikeSpawner(int level, NBTTagCompound entityNbt) {
    // This tags the entity as a "roguelike" entity, by giving the entity mining fatigue.
    // Later, a check for mining fatigue determines if it should equip this entity.
    NBTTagList activeEffects = new NBTTagList();
    activeEffects.appendTag(getMiningFatigueBuff(level));
    entityNbt.setTag("ActiveEffects", activeEffects);
  }

  private NBTTagCompound getMiningFatigueBuff(int level) {
    NBTTagCompound buff = new NBTTagCompound();
    buff.setByte("Id", (byte) EffectType.FATIGUE.getEffectID());
    buff.setByte("Amplifier", (byte) level);
    buff.setInteger("Duration", 10);
    buff.setByte("Ambient", (byte) 0);
    return buff;
  }

  private void setLootingRateTags(NBTTagCompound entityNbt) {
    float lootingChance = (float) RogueConfig.LOOTING.getDouble();

    NBTTagList handDropChances = new NBTTagList();
    NBTTagFloat mainHandDropChance = new NBTTagFloat(lootingChance);
    NBTTagFloat offHandDropChance = new NBTTagFloat(lootingChance);
    handDropChances.appendTag(mainHandDropChance);
    handDropChances.appendTag(offHandDropChance);
    entityNbt.setTag("HandDropChances", handDropChances);


    NBTTagList armorDropChances = new NBTTagList();
    NBTTagFloat feetDropChance = new NBTTagFloat(lootingChance);
    NBTTagFloat legsDropChance = new NBTTagFloat(lootingChance);
    NBTTagFloat chestDropChance = new NBTTagFloat(lootingChance);
    NBTTagFloat headDropChance = new NBTTagFloat(lootingChance);
    armorDropChances.appendTag(feetDropChance);
    armorDropChances.appendTag(legsDropChance);
    armorDropChances.appendTag(chestDropChance);
    armorDropChances.appendTag(headDropChance);
    entityNbt.setTag("ArmorDropChances", armorDropChances);
  }

}
