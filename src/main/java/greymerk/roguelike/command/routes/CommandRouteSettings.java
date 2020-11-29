package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.util.ArgumentParser;

public class CommandRouteSettings extends CommandRouteBase {

  @Override
  public void execute(CommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      context.sendInfo("Usage: roguelike settings [reload | list]");
      return;
    }
    if (ap.match(0, "reload")) {
      try {
        Dungeon.initResolver();
        context.sendSuccess("Settings Reloaded");
      } catch (Exception exception) {
        if (exception.getMessage() == null) {
          context.sendFailure("Uncaught Exception");
        } else {
          context.sendFailure(exception);
        }
      }
      return;
    }

    if (ap.match(0, "list")) {
      try {
        SettingsResolver settingsResolver = SettingsResolver.initSettingsResolver();
        if (ap.hasEntry(1)) {
          String namespace = ap.get(1);
          context.sendSuccess(settingsResolver.toString(namespace));
          return;
        }
        context.sendSuccess(settingsResolver.toString());
      } catch (Exception exception) {
        context.sendFailure(exception);
      }
    }
  }
}
