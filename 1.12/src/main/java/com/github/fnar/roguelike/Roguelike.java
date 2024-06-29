package com.github.fnar.roguelike;

import com.github.fnar.roguelike.command.RoguelikeCommand1_12;
import com.github.fnar.roguelike.worldgen.DungeonGenerator1_12;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import greymerk.roguelike.EntityJoinWorld1_12;
import greymerk.roguelike.ItemTooltip;
import greymerk.roguelike.dungeon.Dungeon;

@Mod(modid = "roguelike", name = "Roguelike Dungeons -- Fnar Edition", version = Roguelike.version, acceptableRemoteVersions = "*")
public class Roguelike {

  public static final Logger LOGGER = LogManager.getLogger(Dungeon.MOD_ID);

  public static final String version = "2.4.5";
  public static final String date = "2024/6/29";
  // The instance of your mod that Forge uses.
  @Instance("roguelike")
  public static Roguelike instance;
  public static DungeonGenerator1_12 roguelikeDungeonGenerator = new DungeonGenerator1_12();

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    GameRegistry.registerWorldGenerator(roguelikeDungeonGenerator, 0);
  }

  @EventHandler
  public void modInit(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new EntityJoinWorld1_12());
    MinecraftForge.EVENT_BUS.register(new ItemTooltip());
  }

  @EventHandler
  public void serverStart(FMLServerStartingEvent event) {
    MinecraftServer server = event.getServer();
    ICommandManager command = server.getCommandManager();
    ServerCommandManager serverCommand = ((ServerCommandManager) command);
    serverCommand.registerCommand(new RoguelikeCommand1_12());
  }
}
