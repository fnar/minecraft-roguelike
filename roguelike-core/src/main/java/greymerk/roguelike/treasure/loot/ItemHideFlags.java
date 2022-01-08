package greymerk.roguelike.treasure.loot;

import java.util.Optional;

import static java.util.Arrays.stream;

public enum ItemHideFlags {

  ENCHANTMENTS,
  ATTRIBUTES,
  UNBREAKABLE,
  CANDESTROY,
  CANPLACEON,
  EFFECTS;

  public static Optional<Integer> reduce(ItemHideFlags[] flags) {
    return stream(flags)
        .map(ItemHideFlags::get)
        .reduce(Integer::sum);
  }

  public static int get(ItemHideFlags flag) {
    switch (flag) {
      case ENCHANTMENTS:
        return 1;
      case ATTRIBUTES:
        return 2;
      case UNBREAKABLE:
        return 4;
      case CANDESTROY:
        return 8;
      case CANPLACEON:
        return 16;
      case EFFECTS:
        return 32;
      default:
        return 0;
    }
  }


}
