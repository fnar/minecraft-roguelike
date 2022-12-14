package com.github.fnar.minecraft.tag;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

import lombok.ToString;

import static com.github.fnar.minecraft.tag.TagType.LIST;

@ToString
public class ListTag implements Tag {

  private final List<Tag> tags = Lists.newLinkedList();

  @Override
  public TagType getType() {
    return LIST;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public ListTag withTags(Tag... tags) {
    this.tags.addAll(Arrays.asList(tags));
    return this;
  }

  public ListTag withTags(String... values) {
    Arrays.stream(values).forEach(value -> this.withTags(new StringTag(value)));
    return this;
  }
}
