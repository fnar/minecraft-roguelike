package greymerk.roguelike.command;

public interface CommandBase {

  boolean doesStringStartWith(String original, String region);

  int parseInt(String input);

}
