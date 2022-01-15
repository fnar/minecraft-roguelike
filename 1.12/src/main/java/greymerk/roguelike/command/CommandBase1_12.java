package greymerk.roguelike.command;

import net.minecraft.command.NumberInvalidException;

public class CommandBase1_12 implements CommandBase{

  @Override
  public boolean doesStringStartWith(String original, String region) {
    return net.minecraft.command.CommandBase.doesStringStartWith(original, region);
  }

  @Override
  public int parseInt(String input) {
    try {
      return net.minecraft.command.CommandBase.parseInt(input);
    } catch (NumberInvalidException e) {
      throw new RuntimeException(e);
    }
  }
}
