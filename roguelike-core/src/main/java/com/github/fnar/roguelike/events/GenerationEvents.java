package com.github.fnar.roguelike.events;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.worldgen.Coord;

public interface GenerationEvents {
    void eventPre(SettingIdentifier id, Coord coord);

    void eventPost(SettingIdentifier id, Coord coord);

    boolean eventSuggest(SettingIdentifier id, Coord coord);
}
