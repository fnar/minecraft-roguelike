package com.github.fnar.roguelike.command.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.command.ISuggestionProvider;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class SettingIdentifierArgumentType implements ArgumentType<SettingIdentifier> {

  public static SettingIdentifierArgumentType newSettingIdentifierArgumentType() {
    return new SettingIdentifierArgumentType();
  }

  @Override
  public SettingIdentifier parse(StringReader reader) throws CommandSyntaxException {
    String first = reader.readString();
    if (!reader.canRead()) {
      return new SettingIdentifier(SettingsContainer.DEFAULT_NAMESPACE, first);
    }
    reader.expect(SettingIdentifier.DELIMITER);
    if (!reader.canRead()) {
      return new SettingIdentifier(SettingsContainer.DEFAULT_NAMESPACE, first);
    }
    String second = reader.readString();
    return new SettingIdentifier(first, second);
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
    Collection<String> settingIdentifiers = SettingsResolver.getInstance(requiredModName -> true)
        .getAllSettingIdentifiers().stream()
        .map(SettingIdentifier::toString)
        .collect(Collectors.toSet());
    return ISuggestionProvider.suggest(settingIdentifiers, builder);
  }
}
