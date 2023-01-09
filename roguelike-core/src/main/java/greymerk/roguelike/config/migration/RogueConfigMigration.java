package greymerk.roguelike.config.migration;

import java.util.function.Consumer;

import greymerk.roguelike.config.ConfigurationMap;

public interface RogueConfigMigration extends Consumer<ConfigurationMap> { }
