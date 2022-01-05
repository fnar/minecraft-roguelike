package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Book;
import greymerk.roguelike.treasure.loot.books.BookStarter;

public class ItemBook extends LootItem {

  Book.Special type;

  public ItemBook(Book.Special type) {
    this(type, 1, 0);
  }

  public ItemBook(Book.Special type, int weight, int level) {
    super(weight, level);
    this.type = type;
  }

  @Override
  public RldItemStack getLootItem(Random random, int level) {
    return new BookStarter().asStack();
  }

}
