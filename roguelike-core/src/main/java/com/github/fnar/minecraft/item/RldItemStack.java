package com.github.fnar.minecraft.item;


import com.github.fnar.minecraft.tag.CompoundTag;
import com.github.fnar.minecraft.tag.ListTag;

import greymerk.roguelike.treasure.loot.ItemHideFlags;

public class RldItemStack {

  private RldItem item;
  private int count;
  private int damage;
  private CompoundTag tags;
  private boolean isPlzNbt = false;
  private String plzNbt;

  public RldItemStack(RldItem item, int count) {
    this.item = item;
    this.count = count;
  }

  public RldItem getItem() {
    return item;
  }

  public RldItemStack withItem(RldItem item) {
    this.item = item;
    return this;
  }

  public int getCount() {
    return count;
  }

  public RldItemStack withCount(int count) {
    this.count = count;
    return this;
  }

  public int getDamage() {
    return damage;
  }

  public RldItemStack withDamage(int damage) {
    this.damage = damage;
    return this;
  }

  public RldItemStack withTag(String name, CompoundTag value) {
    ensureTags().withTag(name, value);
    return this;
  }

  public RldItemStack withTag(String name, int value) {
    ensureTags().withTag(name, value);
    return this;
  }

  public RldItemStack withTag(String name, String value) {
    ensureTags().withTag(name, value);
    return this;
  }

  public CompoundTag getTags() {
    return tags;
  }

  private CompoundTag ensureTags() {
    if (tags == null) {
      tags = new CompoundTag();
    }
    return tags;
  }

  public RldItemStack withDisplayName(String name) {
    ensureCompoundTag("display").withTag("Name", name);
    return this;
  }

  public CompoundTag ensureCompoundTag(String name) {
    CompoundTag tag = ensureTags().getCompound(name);
    if (tag != null) {
      return tag;
    }
    tag = new CompoundTag();
    withTag(name, tag);
    return tag;
  }

  public RldItemStack withDisplayLore(String lore) {
    ensureCompoundTag("display").withTag("Lore", new ListTag().withTag(lore));
    return this;
  }

  public RldItemStack withHideFlag(ItemHideFlags... hideFlags) {
    ItemHideFlags.reduce(hideFlags)
        .ifPresent(integer -> withTag("HideFlags", integer));
    return this;
  }

  public boolean isPlzNbt() {
    return isPlzNbt;
  }

  public RldItemStack plzNbt(String json) {
    this.isPlzNbt = true;
    this.plzNbt = json;
    return this;
  }

  public String getPlzNbt() {
    return plzNbt;
  }

}
