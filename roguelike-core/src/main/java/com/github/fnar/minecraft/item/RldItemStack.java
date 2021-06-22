package com.github.fnar.minecraft.item;


import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.tag.CompoundTag;
import com.github.fnar.minecraft.tag.ListTag;

import greymerk.roguelike.treasure.loot.ItemHideFlags;

public class RldItemStack {

  private RldItem item;
  private int count;
  private CompoundTag tags;

  public RldItemStack(RldItem item) {
    this(item, 1);
  }

  public RldItemStack(RldItem item, int count) {
    this.item = item;
    this.count = count;
  }

  public static RldItemStack forBlockType(BlockType blockType) {
    return new RldItemStack(new Block(blockType));
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

  public boolean isTagged() {
    return tags != null;
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
}
