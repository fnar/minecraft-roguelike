package greymerk.roguelike.treasure.loot.books;

import greymerk.roguelike.treasure.loot.Book;

public class BookStarter extends Book {

  public BookStarter() {
    super("greymerk", "Roguelike Dungeons");

    this.addPage(
        "Roguelike Dungeons\n" +
//            "v" + Roguelike.version + " (" + Roguelike.date + ")\n\n" +
            "Thank you for playing <3\n\n" +
            "    - Credits - \n" +
            "Author: @Greymerk\n" +
            "Bits: Drainedsoul\n" +
            "Ideas: Eniko @enichan\n" +
            "Contributions: Fnar\n"
    );
  }
}
