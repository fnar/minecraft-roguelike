package com.github.fnar.roguelike.command;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.exception.NoValidLocationException;
import greymerk.roguelike.command.exception.SettingNameNotFoundException;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsRandom;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.dungeon.settings.TestDungeonSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class GenerateDungeonCommand extends BaseRoguelikeCommand {

  private final Coord coord;
  private final String settingName;

  public GenerateDungeonCommand(CommandContext commandContext, String settingName, Coord coord) {
    super(commandContext);
    this.settingName = settingName;
    this.coord = coord;
  }

  @Override
  public void onRun() throws Exception {
    WorldEditor editor = commandContext.createEditor();
    SettingsContainer settingsContainer = new SettingsContainer(commandContext.getModLoader()).loadFiles();
    SettingsResolver.instance = new SettingsResolver(settingsContainer);
    DungeonSettings dungeonSettings = chooseDungeonSettings(settingName, coord, editor);
    generateDungeon(commandContext, coord, editor, dungeonSettings);
  }

  private DungeonSettings chooseDungeonSettings(String settingName, Coord pos, WorldEditor editor) throws NoValidLocationException, SettingNameNotFoundException {
    if (settingName == null) {
      return resolveAnyCustomDungeonSettings(pos, editor);
    } else if (settingName.equals("test")) {
      return resolveTestDungeon(editor, pos);
    } else if (settingName.equals("random")) {
      return resolveRandomDungeon(editor, pos);
    } else {
      return resolveNamedDungeonSettings(settingName);
    }
  }

  private DungeonSettings resolveAnyCustomDungeonSettings(Coord pos, WorldEditor editor) throws NoValidLocationException {
    Optional<DungeonSettings> dungeonSettings = SettingsResolver.instance.chooseDungeonSetting(editor, pos);
    return dungeonSettings
        .orElseThrow(() -> new NoValidLocationException(pos));
  }

  private DungeonSettings resolveTestDungeon(WorldEditor editor, Coord pos) {
    Random random = editor.getRandom();
    random.setSeed(editor.getSeed(pos));
    return new TestDungeonSettings(random);
  }

  private DungeonSettings resolveRandomDungeon(WorldEditor editor, Coord pos) {
    Random random = editor.getRandom();
    random.setSeed(editor.getSeed(pos));
    return new SettingsRandom(random);
  }

  private DungeonSettings resolveNamedDungeonSettings(String settingName) throws SettingNameNotFoundException {
    DungeonSettings dungeonSettings = SettingsResolver.instance.getByName(settingName);
    return Optional.ofNullable(dungeonSettings)
        .orElseThrow(() -> new SettingNameNotFoundException(settingName));
  }

  private void generateDungeon(CommandContext context, Coord coord, WorldEditor editor, DungeonSettings dungeonSettings) {
    Dungeon dungeon = new Dungeon(editor, context.getModLoader());
    dungeon.generate(dungeonSettings, coord);
    context.sendSuccess("generateddungeon", String.format("%s at %s.", dungeonSettings.getId(), coord));
  }

}
