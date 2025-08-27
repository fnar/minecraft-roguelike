package com.github.fnar.roguelike.events;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import lombok.NonNull;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import java.util.List;

public class StructurePartsGenerationEvent extends WorldEvent implements GenerationPartsEvent {

    protected World world;
    protected List<Float> coords;
    protected SettingIdentifier identifier;
    protected int dimension;

    public StructurePartsGenerationEvent(World world, int dimension) {
        super(world);
        this.world = world;
        this.dimension = dimension;
    }

    public StructurePartsGenerationEvent(World world, @NonNull SettingIdentifier id, @NonNull List<Float> coords) {
        super(world);
        this.identifier = id;
        this.coords = coords;
    }

    @NonNull
    public SettingIdentifier getIdentifier() {
        return identifier;
    }

    @NonNull
    public List<Float> getCoords() {
        return coords;
    }

    public World getWorld() {
        return world;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public void eventParts(SettingIdentifier id, List<Float> coords) {
        MinecraftForge.EVENT_BUS.post(new StructurePartsGenerationEvent(world, id, coords));
    }
}
