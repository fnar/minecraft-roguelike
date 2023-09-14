package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.ReloadSettingsCommand;
import com.github.fnar.roguelike.command.SimpleRoguelikeCommand;

import java.util.List;
import java.util.function.Consumer;

import greymerk.roguelike.command.CommandBase;
import greymerk.roguelike.command.CommandContext1_12;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.util.ArgumentParser;

public class CommandRouteSettings extends CommandRouteBase {

  public CommandRouteSettings(CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext1_12 commandContext, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);

    if (!argumentParser.hasEntry(0)) {
      commandContext.sendInfo("notif.roguelike.usage_", "roguelike settings [reload | list]");
      return;
    }

    if (argumentParser.match(0, "reload")) {
      new ReloadSettingsCommand(
          commandContext,
          () -> commandContext.sendSuccess("settingsreloaded"),
          commandContext::sendFailure
      ).run();
    }

    if (argumentParser.match(0, "list")) {
      String namespace = argumentParser.hasEntry(1) ? argumentParser.get(1) : "";

      Consumer<Exception> onException = commandContext::sendFailure;
      Runnable onSuccess = () -> commandContext.sendSuccess("settingslisted");
      Runnable onRun = () -> {
        try {
          SettingsContainer settingsContainer = new SettingsContainer(commandContext.getModLoader());
          settingsContainer.loadFiles();
          SettingsResolver settingsResolver = new SettingsResolver(settingsContainer);

          if (namespace.isEmpty()) {
            commandContext.sendInfo(settingsResolver.toString());
          } else {
            commandContext.sendInfo(settingsResolver.toString(namespace));
          }
        } catch (Exception exception) {
          onException.accept(exception);
        }
      };
      new SimpleRoguelikeCommand(onRun, onSuccess, onException).run();
    }
  }

}
