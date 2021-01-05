package greymerk.roguelike.dungeon.rooms;

public enum Frequency {
  RANDOM,
  SECRET,
  SINGLE;

  public boolean isSingle() {
    return equals(SINGLE);
  }

  public boolean isRandom() {
    return equals(RANDOM);
  }

  public boolean isSecret() {
    return equals(SECRET);
  }

}
