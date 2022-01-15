package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.ReloadSettingsCommand;
import com.github.fnar.roguelike.command.SimpleRoguelikeCommand;

import java.util.List;
import java.util.function.Consumer;

import greymerk.roguelike.command.CommandBase;
import greymerk.roguelike.command.CommandContext1_12;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.util.ArgumentParser;

public class CommandRouteSettings extends CommandRouteBase {

  public CommandRouteSettings(CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext1_12 context, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);

    if (!argumentParser.hasEntry(0)) {
      context.sendInfo("Usage: roguelike settings [reload | list]");
      return;
    }

    if (argumentParser.match(0, "reload")) {
      new ReloadSettingsCommand(
          () -> context.sendSuccess("Settings reloaded successfully."),
          context::sendFailure
      ).run();
    }

    if (argumentParser.match(0, "list")) {
      String namespace = argumentParser.hasEntry(1) ? argumentParser.get(1) : "";

      Consumer<Exception> onException = context::sendFailure;
      Runnable onSuccess = () -> context.sendSuccess("Settings listed successfully.");
      Runnable onRun = () -> {
        try {
          SettingsResolver settingsResolver = SettingsResolver.initSettingsResolver();
          if (namespace.isEmpty()) {
            context.sendInfo(settingsResolver.toString());
          } else {
            context.sendInfo(settingsResolver.toString(namespace));
          }
        } catch (Exception exception) {
          onException.accept(exception);
        }
      };
      new SimpleRoguelikeCommand(onRun, onSuccess, onException).run();
    }
  }

}
