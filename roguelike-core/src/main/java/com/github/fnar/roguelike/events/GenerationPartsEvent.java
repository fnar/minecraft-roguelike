package com.github.fnar.roguelike.events;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;

public interface GenerationPartsEvent {

    void eventParts(SettingIdentifier id, java.util.List<Float> coords);
}
