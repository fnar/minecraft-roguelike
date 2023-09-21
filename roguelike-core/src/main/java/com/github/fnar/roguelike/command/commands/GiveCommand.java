package com.github.fnar.roguelike.command.commands;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.command.CommandContext;

import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class GiveCommand extends BaseRoguelikeCommand {

  private final String itemName;

  public GiveCommand(CommandContext commandContext, String itemName) {
    super(commandContext);
    this.itemName = itemName;
  }

  @Override
  public void onRun() {
    RldItemStack item = ItemNovelty.getItemByName(itemName);
    if (item == null) {
      commandContext.sendFailure("nosuchitem");
      return;
    }
    commandContext.give(item);
  }

  @Override
  public void onSuccess() {
    commandContext.sendSuccess("given", itemName);
  }

  private static RldItemStack onRun(String name) {
    return ItemNovelty.getItemByName(name);
  }
}
