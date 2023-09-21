package com.github.fnar.roguelike;

import com.github.fnar.roguelike.command.RoguelikeCommands;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("roguelike")
public class Roguelike {

  public static final String version = "2.4.4";
  public static final String date = "2023/2/27";
  // The instance of your mod that Forge uses.
//  @Instance("roguelike")
//  public static Roguelike instance;
//  // Says where the client and server 'proxy' code is loaded.
//  @SidedProxy(clientSide = "greymerk.roguelike.ClientProxy", serverSide = "greymerk.roguelike.CommonProxy")
//  public static CommonProxy proxy;
//  public static DungeonGenerator worldGen = new DungeonGenerator();
//
//  @EventHandler
//  public void preInit(FMLPreInitializationEvent event) {
//    GameRegistry.registerWorldGenerator(worldGen, 0);
//  }
//
//  @EventHandler
//  public void modInit(FMLInitializationEvent event) {
//    MinecraftForge.EVENT_BUS.register(new EntityJoinWorld());
//  }
//
//  @EventHandler
//  public void serverStart(FMLServerStartingEvent event) {
//    MinecraftServer server = event.getServer();
//    ICommandManager command = server.getCommandManager();
//    ServerCommandManager serverCommand = ((ServerCommandManager) command);
//    serverCommand.registerCommand(new CommandRoguelike());
//  }

  // Directly reference a log4j logger.
  private static final Logger LOGGER = LogManager.getLogger();

  public Roguelike() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    // Register the enqueueIMC method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
    // Register the processIMC method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
    // Register the doClientStuff method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void setup(final FMLCommonSetupEvent event) {
    // some preinit code
    LOGGER.info("HELLO FROM PREINIT");
    LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    // do something that can only be done on the client
    LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
  }

  private void enqueueIMC(final InterModEnqueueEvent event) {
    // some example code to dispatch IMC to another mod
    InterModComms.sendTo("examplemod", "helloworld", () -> {
      LOGGER.info("Hello world from the MDK");
      return "Hello world";
    });
  }

  private void processIMC(final InterModProcessEvent event) {
    // some example code to receive and process InterModComms from other mods
    LOGGER.info("Got IMC {}", event.getIMCStream().
        map(m -> m.getMessageSupplier().get()).
        collect(Collectors.toList()));
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    // do something when the server starts
    LOGGER.info("HELLO from server starting");

    CommandDispatcher<CommandSource> commandDispatcher = event.getServer().getCommandManager().getDispatcher();

    commandDispatcher.register(RoguelikeCommands.roguelikeCommand());
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
      // register a new block here
      LOGGER.info("HELLO from Register Block");
    }
  }
}
