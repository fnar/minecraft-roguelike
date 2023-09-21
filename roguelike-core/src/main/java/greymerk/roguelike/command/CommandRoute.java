package greymerk.roguelike.command;

import java.util.List;

public interface CommandRoute {

  void execute(CommandContext context, List<String> args);

  List<String> getTabCompletion(List<String> args);

}
