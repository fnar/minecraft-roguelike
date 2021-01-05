package greymerk.roguelike.command;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface CommandSender {

  void sendMessage(String message, MessageType type);

  void give(ItemStack item);

  WorldEditor createWorldEditor();

  Coord getPos();
}
