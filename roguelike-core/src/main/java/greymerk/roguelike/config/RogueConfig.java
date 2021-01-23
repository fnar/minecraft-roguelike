package greymerk.roguelike.config;


import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.List;

public enum RogueConfig {

  DIMENSIONBL("dimensionBL", false, 0, 0.0, Lists.newArrayList()),
  DIMENSIONWL("dimensionWL", false, 0, 0.0, Lists.newArrayList(0)),
  DONATURALSPAWN("doNaturalSpawn", true, 0, 0.0, null),
  ENCASE("encase", false, 0, 0.0, null),
  FURNITURE("furniture", true, 0, 0.0, null),
  GENEROUS("generous", true, 0, 0.0, null),
  LOOTING("looting", false, 0, 0.085D, null),
  LOWERLIMIT("lowerLimit", false, 60, 0.0, null),
  MOBDROPS("mobDrops", false, 0, 0.0, null),
  PRECIOUSBLOCKS("preciousBlocks", true, 0, 0.0, null),
  RANDOM("random", false, 0, 0.0, null),
  ROGUESPAWNERS("rogueSpawners", true, 0, 0.0, null),
  SPAWNBUILTIN("doBuiltinSpawn", true, 0, 0.0, null),
  SPAWNCHANCE("spawnChance", false, 0, 1.0, null),
  SPAWNFREQUENCY("spawnFrequency", false, 10, 0.0, null),
  SPAWN_ATTEMPTS("spawnAttempts", false, 10, 0.0, null),
  SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES("spawnMinimumDistanceFromVanillaStructures", false, 50, 0.0, null),
  UPPERLIMIT("upperLimit", false, 100, 0.0, null),
  ;

  RogueConfig(
      String name,
      Boolean defaultBoolean,
      Integer defaultInt,
      Double defaultDouble,
      List<Integer> defaultIntList
  ) {
    this.name = name;
    this.defaultBoolean = defaultBoolean;
    this.defaultInt = defaultInt;
    this.defaultDouble = defaultDouble;
    this.defaultIntList = defaultIntList;
  }

  private final String name;
  private final Boolean defaultBoolean;
  private final Integer defaultInt;
  private final Double defaultDouble;
  private final List<Integer> defaultIntList;

  public static final String configDirName = "config/roguelike_dungeons";
  public static final String configFileName = "roguelike.cfg";

  public static boolean testing = false;
  private static ConfigFile instance = null;

  @SuppressWarnings("unchecked")
  private static void setDefaults() {
    if (!instance.ContainsKey(DONATURALSPAWN.name)) {
      setBoolean(DONATURALSPAWN, DONATURALSPAWN.defaultBoolean);
    }
    if (!instance.ContainsKey(SPAWNFREQUENCY.name)) {
      setInt(SPAWNFREQUENCY, SPAWNFREQUENCY.defaultInt);
    }
    if (!instance.ContainsKey(SPAWNCHANCE.name)) {
      setDouble(SPAWNCHANCE, SPAWNCHANCE.defaultDouble);
    }
    if (!instance.ContainsKey(GENEROUS.name)) {
      setBoolean(GENEROUS, GENEROUS.defaultBoolean);
    }
    if (!instance.ContainsKey(DIMENSIONWL.name)) {
      setIntList(DIMENSIONWL, DIMENSIONWL.defaultIntList);
    }
    if (!instance.ContainsKey(DIMENSIONBL.name)) {
      setIntList(DIMENSIONBL, DIMENSIONBL.defaultIntList);
    }
    if (!instance.ContainsKey(PRECIOUSBLOCKS.name)) {
      setBoolean(PRECIOUSBLOCKS, PRECIOUSBLOCKS.defaultBoolean);
    }
    if (!instance.ContainsKey(LOOTING.name)) {
      setDouble(LOOTING, LOOTING.defaultDouble);
    }
    if (!instance.ContainsKey(UPPERLIMIT.name)) {
      setInt(UPPERLIMIT, UPPERLIMIT.defaultInt);
    }
    if (!instance.ContainsKey(LOWERLIMIT.name)) {
      setInt(LOWERLIMIT, LOWERLIMIT.defaultInt);
    }
    if (!instance.ContainsKey(ROGUESPAWNERS.name)) {
      setBoolean(ROGUESPAWNERS, ROGUESPAWNERS.defaultBoolean);
    }
    if (!instance.ContainsKey(ENCASE.name)) {
      setBoolean(ENCASE, ENCASE.defaultBoolean);
    }
    if (!instance.ContainsKey(FURNITURE.name)) {
      setBoolean(FURNITURE, FURNITURE.defaultBoolean);
    }
    if (!instance.ContainsKey(RANDOM.name)) {
      setBoolean(RANDOM, RANDOM.defaultBoolean);
    }
    if (!instance.ContainsKey(SPAWNBUILTIN.name)) {
      setBoolean(SPAWNBUILTIN, SPAWNBUILTIN.defaultBoolean);
    }
    if (!instance.ContainsKey(SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.name)) {
      setInt(SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES, SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.defaultInt);
    }
    if (!instance.ContainsKey(SPAWN_ATTEMPTS.name)) {
      setInt(SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES, SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.defaultInt);
    }
  }

  public static double getDouble(RogueConfig option) {
    if (testing) {
      return option.defaultDouble;
    }
    reload(false);
    return instance.GetDouble(option.name, option.defaultDouble);
  }

  public static void setDouble(RogueConfig option, double value) {
    reload(false);
    instance.Set(option.name, value);
  }

  public static boolean getBoolean(RogueConfig option) {
    if (testing) {
      return option.defaultBoolean;
    }
    reload(false);
    return instance.GetBoolean(option.name, option.defaultBoolean);
  }

  public static void setBoolean(RogueConfig option, Boolean value) {
    reload(false);
    instance.Set(option.name, value);
  }

  public static int getInt(RogueConfig option) {
    if (testing) {
      return option.defaultInt;
    }
    reload(false);
    return instance.GetInteger(option.name, option.defaultInt);
  }

  public static void setInt(RogueConfig option, int value) {
    reload(false);
    instance.Set(option.name, value);
  }

  @SuppressWarnings("unchecked")
  public static List<Integer> getIntList(RogueConfig option) {
    if (testing) {
      return option.defaultIntList;
    }
    reload(false);
    return instance.GetListInteger(option.name, option.defaultIntList);
  }

  public static void setIntList(RogueConfig option, List<Integer> value) {
    reload(false);
    instance.Set(option.name, value);
  }

  private static void init() {

    if (testing) {
      return;
    }

    // make sure file exists
    File configDir = new File(configDirName);
    if (!configDir.exists()) {
      configDir.mkdir();
    }

    File cfile = new File(configDirName + "/" + configFileName);

    if (!cfile.exists()) {
      try {
        cfile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // read in configs
    try {
      instance = new ConfigFile(configDirName + "/" + configFileName, new INIParser());
    } catch (Exception e) {
      e.printStackTrace();
    }

    setDefaults();

    try {
      instance.Write();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void reload(boolean force) {
    if (instance == null || force) {
      init();
    }
  }

}
