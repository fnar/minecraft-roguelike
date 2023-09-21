package greymerk.roguelike.command;

import com.github.fnar.util.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseCommandRoute implements CommandRoute {

  private final Map<String, CommandRoute> routes;

  public BaseCommandRoute() {
    this.routes = new HashMap<>();
  }

  protected void addRoute(String id, CommandRoute route) {
    this.routes.put(id, route);
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    if (args.isEmpty() || !this.routes.containsKey(args.get(0))) {
      return;
    }
    List<String> tail = new ArrayList<>(args);
    String head = tail.remove(0);
    this.routes.get(head).execute(context, tail);
  }

  @Override
  public List<String> getTabCompletion(List<String> args) {
    if (args.size() == 1) {
      return getListTabOptions(args.get(0), this.routes.keySet());
    }

    if (args.size() <= 1) {
      return Collections.emptyList();
    }
    List<String> tail = new ArrayList<>(args);
    String head = tail.remove(0);

    if (this.routes.containsKey(head)) {
      return this.routes.get(head).getTabCompletion(tail);
    }

    return Collections.emptyList();
  }

  protected List<String> getListTabOptions(String name, Collection<String> possibilities) {
    List<String> options = new ArrayList<>();
    for (String item : possibilities) {
      if (Strings.startWith(name, item)) {
        options.add(item);
      }
    }

    return options;
  }

}
