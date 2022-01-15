package greymerk.roguelike.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandRouteBase implements ICommandRouter {

  private final Map<String, ICommandRouter> routes;
  protected final greymerk.roguelike.command.CommandBase commandBase;

  public CommandRouteBase(greymerk.roguelike.command.CommandBase commandBase) {
    this.routes = new HashMap<>();
    this.commandBase = commandBase;
  }

  protected void addRoute(String id, ICommandRouter route) {
    this.routes.put(id, route);
  }

  @Override
  public void execute(CommandContext1_12 context, List<String> args) {
    if (args.size() > 0) {
      if (this.routes.containsKey(args.get(0))) {
        List<String> tail = new ArrayList<>(args);
        String head = tail.remove(0);
        this.routes.get(head).execute(context, tail);
      }
    }
  }

  @Override
  public List<String> getTabCompletion(List<String> args) {
    if (args.size() == 1) {
      return getListTabOptions(args.get(0), this.routes.keySet());
    }

    if (args.size() > 1) {
      List<String> tail = new ArrayList<>(args);
      String head = tail.remove(0);

      if (this.routes.containsKey(head)) {
        return this.routes.get(head).getTabCompletion(tail);
      }
    }

    return Collections.emptyList();
  }

  protected List<String> getListTabOptions(String name, Collection<String> possibilities) {
    List<String> options = new ArrayList<>();
    for (String item : possibilities) {
      if (commandBase.doesStringStartWith(name, item)) {
        options.add(item);
      }
    }

    return options;
  }

}
