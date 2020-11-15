package greymerk.roguelike.command;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ICommandContext {

  void sendMessage(String message, MessageType type);

  WorldEditor createEditor();

  Coord getPos();

  void give(ItemStack item);

}
