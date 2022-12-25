package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class Tower {

  protected WorldEditor editor;
  protected Theme theme;

  public Tower(WorldEditor worldEditor, Theme theme) {
    this.editor = worldEditor;
    this.theme = theme;
  }

  public void chest(WorldEditor worldEditor, Direction dir, Coord coord) {
    new TreasureChest(coord, worldEditor)
        .withChestType(ChestType.STARTER)
        .withTrap(false)
        .withFacing(dir)
        .stroke(worldEditor, coord);
  }

  public abstract void generate(Coord origin);

  protected BlockBrush getPrimaryWall() {
    return theme.getPrimary().getWall();
  }

  protected BlockBrush getPrimaryPillar() {
    return theme.getPrimary().getPillar();
  }

  protected StairsBlock getPrimaryStair() {
    return theme.getPrimary().getStair();
  }

  protected DoorBlock getPrimaryDoor() {
    return theme.getPrimary().getDoor();
  }

  protected BlockBrush getPrimaryFloor() {
    return theme.getPrimary().getFloor();
  }

  protected BlockBrush getSecondaryFloor() {
    return theme.getSecondary().getFloor();
  }

  protected BlockBrush getSecondaryPillar() {
    return theme.getSecondary().getPillar();
  }

  protected StairsBlock getSecondaryStair() {
    return theme.getSecondary().getStair();
  }

  protected BlockBrush getSecondaryWall() {
    return theme.getSecondary().getWall();
  }

  protected BlockBrush getPrimaryLight() {
    return theme.getPrimary().getLightBlock();
  }
}
