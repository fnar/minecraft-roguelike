package greymerk.roguelike.monster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.github.fnar.minecraft.Difficulty;
import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.Armour;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.RldBaseItem;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.minecraft.item.WeaponType;
import com.github.fnar.roguelike.loot.special.armour.SpecialArmour;
import com.github.fnar.util.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.provider.LootItem;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

import static greymerk.roguelike.treasure.loot.Equipment.rollQuality;

public class Mob {

  private boolean isChild = false;
  private final Map<Slot, RldItemStack> items = Maps.newEnumMap(Slot.class);
  private String name;

  public Mob withRandomEquipment(int level, Random random) {
    RldBaseItem mainhand = chooseMainhand(random, level);
    if (mainhand != null) {
      equipMainhand(mainhand.asStack());
    }

    RldBaseItem offhand = chooseOffhand(random);
    if (offhand != null) {
      equipOffhand(offhand.asStack());
    }

    equip(Slot.FEET, chooseArmourItem(random, level, ArmourType.BOOTS).asStack());
    equip(Slot.LEGS, chooseArmourItem(random, level, ArmourType.LEGGINGS).asStack());
    equip(Slot.CHEST, chooseArmourItem(random, level, ArmourType.CHESTPLATE).asStack());
    equip(Slot.HEAD, chooseArmourItem(random, level, ArmourType.HELMET).asStack());

    return this;
  }

  public boolean isChild() {
    return isChild;
  }

  public Map<Slot, RldItemStack> getItems() {
    return items;
  }

  public String getName() {
    return name;
  }

  public void equip(Slot slot, RldItemStack rldItemStack) {
    items.put(slot, rldItemStack);
  }

  public void setChild(boolean isChild) {
    this.isChild = isChild;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void equipArmor(Random rand, int level, Color color, int difficulty) {
    Arrays.stream(ArmourType.values())
        .filter(armourType -> !armourType.equals(ArmourType.HORSE))
        .forEach(armourType ->
            equip(armourType.asSlot(),
                createArmor(rand, level, armourType, color, Difficulty.fromNumber(difficulty))));
  }

  private RldItemStack createArmor(Random random, int level, ArmourType armourType, Color color, Difficulty difficulty) {
    Quality quality = rollQuality(random, level);

    if (LootItem.isSpecial(random)) {
      return SpecialArmour.createArmour(random, quality).complete();
    }

    int enchantmentLevel = LootItem.isEnchanted(difficulty, random, level) ? LootItem.getEnchantmentLevel(random, level) : 0;

    return armourType
        .asItem()
        .withQuality(quality)
        .withColor(color)
        .plzEnchantAtLevel(enchantmentLevel)
        .asStack();
  }

  public void equipBow(Random random, int level, int difficulty) {
    equipMainhand(WeaponLootItem.getBow(random, level, difficulty));
  }

  public void equipArrows(Arrow arrow) {
    equipOffhand(arrow.asStack());
  }

  public void equipShield(Random rand) {
    equipOffhand(Shield.get(rand));
  }

  public void equipOffhand(RldItemStack item) {
    equip(Slot.OFFHAND, item);
  }

  public void equipMainhand(RldItemStack itemStack) {
    equip(Slot.MAINHAND, itemStack);
  }

  public Mob apply(MonsterProfileType monsterProfile, int level, int difficulty, Random random) {
    return monsterProfile.apply(this, level, difficulty, random);
  }

  private static RldBaseItem chooseMainhand(Random random, int level) {
    Quality quality = rollQuality(random, level);
    return random.nextBoolean()
        ? random.nextBoolean()
        ? null
        : ToolType.randomAmong(random, someTools()).asItem().withQuality(quality)
        : WeaponType.random(random).asItem().withQuality(quality);
  }

  private RldBaseItem chooseOffhand(Random random) {
    return random.nextBoolean() ? new com.github.fnar.minecraft.item.Shield() : null;
  }

  private static ArrayList<ToolType> someTools() {
    return Lists.newArrayList(
        ToolType.AXE,
        ToolType.HOE,
        ToolType.PICKAXE,
        ToolType.SHOVEL
    );
  }

  private static Armour chooseArmourItem(Random random, int level, ArmourType armourItem) {
    Quality quality = rollQuality(random, level);
    return armourItem.asItem().withQuality(quality);
  }

  public RldItem getMainhand() {
    return Optional.ofNullable(items.get(Slot.MAINHAND)).map(RldItemStack::getItem).orElse(null);
  }

  public RldItem getOffhand() {
    return Optional.ofNullable(items.get(Slot.OFFHAND)).map(RldItemStack::getItem).orElse(null);
  }

  public RldItem getBoots() {
    return Optional.ofNullable(items.get(Slot.FEET)).map(RldItemStack::getItem).orElse(null);
  }

  public RldItem getLeggings() {
    return Optional.ofNullable(items.get(Slot.LEGS)).map(RldItemStack::getItem).orElse(null);
  }

  public RldItem getChestplate() {
    return Optional.ofNullable(items.get(Slot.CHEST)).map(RldItemStack::getItem).orElse(null);
  }

  public RldItem getHelmet() {
    return Optional.ofNullable(items.get(Slot.HEAD)).map(RldItemStack::getItem).orElse(null);
  }
}
