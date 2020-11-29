package greymerk.roguelike.command;

import java.util.List;

public interface ICommandRouter {

  void execute(CommandContext context, List<String> args);

  List<String> getTabCompletion(List<String> args);

}
