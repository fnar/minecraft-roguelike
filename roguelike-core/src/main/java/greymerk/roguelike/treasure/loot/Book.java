package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.ItemType;
import com.github.fnar.minecraft.item.RldItem;

import java.util.ArrayList;
import java.util.List;

public class Book implements RldItem {

  private final List<String> pages = new ArrayList<>();
  private String author;
  private String title;

  public Book(String author, String title) {
    this.author = author;
    this.title = title;
  }

  public void addPage(String page) {
    this.pages.add(page);
  }

  public List<String> getPages() {
    return pages;
  }

  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.BOOK;
  }

  public enum Special {

    CREDITS

  }
}
