package com.github.fnar.roguelike.events;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SubscriberTester {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onStructureGenerationPost(StructureGenerationEvent.Post event) {
        System.out.println("EVENT POST");
        System.out.println(event.getIdentifier());
        System.out.println(event.getCoord());
        System.out.println("-----------");
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onStructureGenerationPre(StructureGenerationEvent.Pre event) {
        System.out.println("EVENT PRE");
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onStructureGenerationSuggest(StructureGenerationEvent.SuggestGen event) {
        System.out.println("EVENT SUGGEST");
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onStructurePartsGeneration(StructurePartsGenerationEvent event) {
        System.out.println("EVENT PARTS");
        System.out.println("DUNGEON ID:" + event.getIdentifier());
        System.out.println("COORDS: " + event.getCoords());
        System.out.println("WORLD:" + event.getWorld());
        System.out.println("DIMENSION:" + event.getDimension());
    }

}
