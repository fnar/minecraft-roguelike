package greymerk.roguelike.monster;

import com.google.common.collect.Maps;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.provider.ArmourLootItem;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class Mob {

  private boolean isChild = false;
  private final Map<Slot, RldItemStack> items = Maps.newEnumMap(Slot.class);
  private String name;

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
                ArmourLootItem.get(rand, level, armourType, color, difficulty)));
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
}
