package com.github.fnar.roguelike.command;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.command.message.Message;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface CommandSender {

  void sendMessage(Message message);

  void give(RldItemStack item);

  WorldEditor createWorldEditor();

  Coord getCoord();
}
