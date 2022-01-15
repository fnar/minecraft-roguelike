package greymerk.roguelike.command;

import com.github.fnar.minecraft.item.RldItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface CommandSender {

  void sendMessage(String message, MessageType type);

  void give(RldItemStack item);

  WorldEditor createWorldEditor();

  Coord getPos();
}
