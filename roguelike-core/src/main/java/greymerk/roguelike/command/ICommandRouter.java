package greymerk.roguelike.command;

import java.util.List;

public interface ICommandRouter {

  void execute(CommandContext1_12 context, List<String> args);

  List<String> getTabCompletion(List<String> args);

}
