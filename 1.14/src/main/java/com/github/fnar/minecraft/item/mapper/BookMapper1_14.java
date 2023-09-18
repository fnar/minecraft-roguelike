package com.github.fnar.minecraft.item.mapper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.Optional;

import greymerk.roguelike.treasure.loot.Book;

public class BookMapper1_14 extends BaseItemMapper1_14<Book> {

  @Override
  public Class<Book> getClazz() {
    return Book.class;
  }

  public ItemStack map(Book book) {
    ItemStack itemStack = new ItemStack(Items.WRITTEN_BOOK, 1);

    ListNBT nbtPages = new ListNBT();

    for (String page : book.getPages()) {
      ITextComponent text = new StringTextComponent(page);
      String json = ITextComponent.Serializer.toJson(text);
      StringNBT nbtPage = new StringNBT(json);
      nbtPages.add(nbtPage);
    }

    itemStack.setTagInfo("pages", nbtPages);
    itemStack.setTagInfo("author", new StringNBT(Optional.ofNullable(book.getAuthor()).orElse("Anonymous")));
    itemStack.setTagInfo("title", new StringNBT(Optional.ofNullable(book.getTitle()).orElse("Book")));

    return itemStack;
  }
}
