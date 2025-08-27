package com.github.fnar.roguelike.events;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.worldgen.Coord;
import lombok.NonNull;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public class StructureGenerationEvent extends WorldEvent implements GenerationEvents {

    protected SettingIdentifier identifier;
    protected Coord coord;
    protected World world;
    protected int dimension;

    public StructureGenerationEvent(World world, int dimension) {
        super(world);
        this.world = world;
        this.dimension = dimension;
    }

    public StructureGenerationEvent(World world, @NonNull SettingIdentifier id, @NonNull Coord coord) {
        super(world);
        this.identifier = id;
        this.coord = coord;
    }

    @NonNull
    public SettingIdentifier getIdentifier() {
        return identifier;
    }

    @NonNull
    public Coord getCoord() {
        return coord;
    }

    public World getWorld() {
        return world;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public void eventPre(SettingIdentifier id, Coord coord) {
        MinecraftForge.EVENT_BUS.post(new StructureGenerationEvent.Pre(world, id, coord));
    }

    @Override
    public void eventPost(SettingIdentifier id, Coord coord) {
        MinecraftForge.EVENT_BUS.post(new StructureGenerationEvent.Post(world, id, coord));
    }

    @Override
    public boolean eventSuggest(SettingIdentifier id, Coord coord) {
        return !MinecraftForge.EVENT_BUS.post(new SuggestGen(world, id, coord));
    }

    @Cancelable
    public static class SuggestGen extends StructureGenerationEvent {
        public SuggestGen(World world, @NonNull SettingIdentifier id, @NonNull Coord coord) {
            super(world, id, coord);
        }
    }


    public static class Pre extends StructureGenerationEvent {
        public Pre(World world, @NonNull SettingIdentifier id, @NonNull Coord coord) {
            super(world, id, coord);
        }
    }

    public static class Post extends StructureGenerationEvent {
        public Post(World world, @NonNull SettingIdentifier id, @NonNull Coord coord) {
            super(world, id, coord);
        }
    }
}
