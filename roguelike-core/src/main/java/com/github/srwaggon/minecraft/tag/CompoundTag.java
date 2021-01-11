package com.github.srwaggon.minecraft.tag;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

public class CompoundTag implements Tag {

  private final Map<String, Tag> tagMap = Maps.newHashMap();

  public Set<Map.Entry<String, Tag>> getTags() {
    return tagMap.entrySet();
  }

  public void setTag(String name, Tag tag) {
    tagMap.put(name, tag);
  }

  public Tag getTag(String name) {
    return tagMap.get(name);
  }

  public CompoundTag getCompound(String name) {
    Tag tag = getTag(name);
    return tag == null ? null : (CompoundTag) tag;
  }

  public CompoundTag withTag(String name, Tag tag) {
    tagMap.put(name, tag);
    return this;
  }

  public CompoundTag withTag(String name, int value) {
    return this.withTag(name, new IntTag(value));
  }

  public CompoundTag withTag(String name, String value) {
    return this.withTag(name, new StringTag(value));
  }

  @Override
  public TagType getType() {
    return TagType.COMPOUND;
  }

}
