package greymerk.roguelike.treasure.loot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChestTypeTest {

  @Test
  public void twoChestTypesByTheSameNameAreTheSameRegardlessOfCase() {
    assertThat(new ChestType("foo")).isEqualTo(new ChestType("foo"));
    assertThat(new ChestType("Foo")).isEqualTo(new ChestType("Foo"));
    assertThat(new ChestType("foo")).isEqualTo(new ChestType("Foo"));
    assertThat(new ChestType("Foo")).isEqualTo(new ChestType("foo"));
    assertThat(new ChestType("Foo")).isEqualTo(new ChestType("fOO"));
  }

}