package com.github.srwaggon.minecraft.tag;

import com.google.common.collect.Lists;

import java.util.List;

import static com.github.srwaggon.minecraft.tag.TagType.LIST;

public class ListTag implements Tag {

  private final List<Tag> tags = Lists.newLinkedList();

  @Override
  public TagType getType() {
    return LIST;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public ListTag withTag(Tag tag) {
    tags.add(tag);
    return this;
  }

  public ListTag withTag(int value) {
    return this.withTag(new IntTag(value));
  }

  public ListTag withTag(String value) {
    return this.withTag(new StringTag(value));
  }
}
