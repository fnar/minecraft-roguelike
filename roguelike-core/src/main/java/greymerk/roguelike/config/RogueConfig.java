package greymerk.roguelike.config;


import com.google.common.collect.Lists;

import com.github.fnar.util.Strings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import greymerk.roguelike.worldgen.VanillaStructure;

import static greymerk.roguelike.dungeon.Dungeon.MOD_ID;

public class RogueConfig {

  private static final Logger logger = LogManager.getLogger(MOD_ID);

  public static final String configDirName = "config/roguelike_dungeons";
  public static final String configFileName = "roguelike.cfg";

  public static boolean testing = false;
  private static ConfigFile instance = null;

  public static final RogueConfig BREAK_IF_REQUIRED_MOD_IS_MISSING = new RogueConfig("breakIfRequiredModIsMissing", true);
  public static final RogueConfig DIMENSIONBL = new RogueConfig("dimensionBL", Lists.newArrayList());
  public static final RogueConfig DIMENSIONWL = new RogueConfig("dimensionWL", Lists.newArrayList(0));
  public static final RogueConfig DONATURALSPAWN = new RogueConfig("doNaturalSpawn", true);
  public static final RogueConfig ENCASE = new RogueConfig("encase", false);
  public static final RogueConfig FURNITURE = new RogueConfig("furniture", true);
  public static final RogueConfig GENEROUS = new RogueConfig("generous", true);
  public static final RogueConfig LOOTING = new RogueConfig("looting", 0.085D);
  public static final RogueConfig LOWERLIMIT = new RogueConfig("lowerLimit", 60);
  public static final RogueConfig MOBDROPS = new RogueConfig("mobDrops", 0.0);
  public static final RogueConfig PRECIOUSBLOCKS = new RogueConfig("preciousBlocks", true);
  public static final RogueConfig RANDOM = new RogueConfig("random", false);
  public static final RogueConfig ROGUESPAWNERS = new RogueConfig("rogueSpawners", true);
  public static final RogueConfig SPAWNBUILTIN = new RogueConfig("doBuiltinSpawn", true);
  public static final RogueConfig SPAWNCHANCE = new RogueConfig("spawnChance", 1.0);
  public static final RogueConfig SPAWNFREQUENCY = new RogueConfig("spawnFrequency", 10);
  public static final RogueConfig SPAWN_ATTEMPTS = new RogueConfig("spawnAttempts", 10);
  public static final RogueConfig SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES = new RogueConfig("spawnMinimumDistanceFromVanillaStructures", 50);
  public static final RogueConfig UPPERLIMIT = new RogueConfig("upperLimit", 100);
  public static final RogueConfig VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM = new RogueConfig("vanillaStructuresToCheckMinimumDistanceFrom", VanillaStructure.getAllAsCommaDelimitedString());

  private static final boolean DEFAULT_BOOLEAN = false;
  private static final int DEFAULT_INT = 0;
  private static final double DEFAULT_DOUBLE = 0.0;
  private static final List<Integer> DEFAULT_INT_LIST = Collections.unmodifiableList(Lists.newArrayList());
  private static final String DEFAULT_STRING_VALUE = "";

  private final String name;
  private final Boolean defaultBoolean;
  private final Integer defaultInt;
  private final Double defaultDouble;
  private final List<Integer> defaultIntList;
  private final String defaultStringValue;

  RogueConfig(String name, boolean value) {
    this(name, value, DEFAULT_INT, DEFAULT_DOUBLE, null, DEFAULT_STRING_VALUE);
  }

  RogueConfig(String name, int value) {
    this(name, DEFAULT_BOOLEAN, value, DEFAULT_DOUBLE, null, DEFAULT_STRING_VALUE);
  }

  RogueConfig(String name, double value) {
    this(name, DEFAULT_BOOLEAN, DEFAULT_INT, value, null, DEFAULT_STRING_VALUE);
  }

  RogueConfig(String name, List<Integer> value) {
    this(name, DEFAULT_BOOLEAN, DEFAULT_INT, DEFAULT_DOUBLE, value, DEFAULT_STRING_VALUE);
  }

  RogueConfig(String name, String value) {
    this(name, DEFAULT_BOOLEAN, DEFAULT_INT, DEFAULT_DOUBLE, DEFAULT_INT_LIST, value);
  }

  RogueConfig(
      String name,
      Boolean defaultBoolean,
      Integer defaultInt,
      Double defaultDouble,
      List<Integer> defaultIntList,
      String defaultStringValue
  ) {
    this.name = name;
    this.defaultBoolean = defaultBoolean;
    this.defaultInt = defaultInt;
    this.defaultDouble = defaultDouble;
    this.defaultIntList = defaultIntList;
    this.defaultStringValue = defaultStringValue;
  }

  public String getName() {
    return name;
  }

  @SuppressWarnings("unchecked")
  private static void setDefaults() {
    if (!instance.ContainsKey(BREAK_IF_REQUIRED_MOD_IS_MISSING.name)) {
      BREAK_IF_REQUIRED_MOD_IS_MISSING.setBoolean(BREAK_IF_REQUIRED_MOD_IS_MISSING.defaultBoolean);
    }

    if (!instance.ContainsKey(DONATURALSPAWN.name)) {
      DONATURALSPAWN.setBoolean(DONATURALSPAWN.defaultBoolean);
    }
    if (!instance.ContainsKey(SPAWNFREQUENCY.name)) {
      SPAWNFREQUENCY.setInt(SPAWNFREQUENCY.defaultInt);
    }
    if (!instance.ContainsKey(SPAWNCHANCE.name)) {
      setDouble(SPAWNCHANCE, SPAWNCHANCE.defaultDouble);
    }
    if (!instance.ContainsKey(GENEROUS.name)) {
      GENEROUS.setBoolean(GENEROUS.defaultBoolean);
    }
    if (!instance.ContainsKey(DIMENSIONWL.name)) {
      DIMENSIONWL.setIntList(DIMENSIONWL.defaultIntList);
    }
    if (!instance.ContainsKey(DIMENSIONBL.name)) {
      DIMENSIONBL.setIntList(DIMENSIONBL.defaultIntList);
    }
    if (!instance.ContainsKey(PRECIOUSBLOCKS.name)) {
      PRECIOUSBLOCKS.setBoolean(PRECIOUSBLOCKS.defaultBoolean);
    }
    if (!instance.ContainsKey(LOOTING.name)) {
      setDouble(LOOTING, LOOTING.defaultDouble);
    }
    if (!instance.ContainsKey(UPPERLIMIT.name)) {
      UPPERLIMIT.setInt(UPPERLIMIT.defaultInt);
    }
    if (!instance.ContainsKey(LOWERLIMIT.name)) {
      LOWERLIMIT.setInt(LOWERLIMIT.defaultInt);
    }
    if (!instance.ContainsKey(ROGUESPAWNERS.name)) {
      ROGUESPAWNERS.setBoolean(ROGUESPAWNERS.defaultBoolean);
    }
    if (!instance.ContainsKey(ENCASE.name)) {
      ENCASE.setBoolean(ENCASE.defaultBoolean);
    }
    if (!instance.ContainsKey(FURNITURE.name)) {
      FURNITURE.setBoolean(FURNITURE.defaultBoolean);
    }
    if (!instance.ContainsKey(RANDOM.name)) {
      RANDOM.setBoolean(RANDOM.defaultBoolean);
    }
    if (!instance.ContainsKey(SPAWNBUILTIN.name)) {
      SPAWNBUILTIN.setBoolean(SPAWNBUILTIN.defaultBoolean);
    }
    if (!instance.ContainsKey(SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.name)) {
      SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.setInt(SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.defaultInt);
    }
    if (!instance.ContainsKey(SPAWN_ATTEMPTS.name)) {
      SPAWN_ATTEMPTS.setInt(SPAWN_ATTEMPTS.defaultInt);
    }

    if (!instance.ContainsKey(VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM.getName())) {
      VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM.setString(VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM.defaultStringValue);
    }
  }

  public static void setDouble(RogueConfig option, double value) {
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

  public double getDouble() {
    if (testing) {
      return defaultDouble;
    }
    reload(false);
    return instance.GetDouble(name, defaultDouble);
  }

  public boolean getBoolean() {
    if (testing) {
      return defaultBoolean;
    }
    reload(false);
    return instance.GetBoolean(name, defaultBoolean);
  }

  public void setBoolean(Boolean value) {
    reload(false);
    instance.Set(name, value);
  }

  public int getInt() {
    if (testing) {
      return defaultInt;
    }
    reload(false);
    return instance.GetInteger(name, defaultInt);
  }

  public void setInt(int value) {
    reload(false);
    instance.Set(name, value);
  }

  @SuppressWarnings("unchecked")
  public List<Integer> getIntList() {
    if (testing) {
      return defaultIntList;
    }
    reload(false);
    return instance.GetListInteger(name, defaultIntList);
  }

  public void setIntList(List<Integer> value) {
    reload(false);
    instance.Set(name, value);
  }

  public String getString() {
    if (testing) {
      return defaultStringValue;
    }
    return instance.GetString(name, defaultStringValue);
  }

  private void setString(String defaultStringValue) {
    reload(false);
    instance.Set(name, defaultStringValue);
  }

  public static Set<VanillaStructure> vanillaStructuresToCheckDistanceTo() {

    return Strings.splitCommas(VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM.getString()).stream()
        .map(str -> str.toUpperCase(Locale.ENGLISH))
        .map(structureName -> {
          try {
            return VanillaStructure.valueOf(structureName);
          } catch (IllegalArgumentException exception) {
            logger.error("Invalid value for config `vanillaStructuresToCheckMinimumDistanceFrom`: \"{}\". Skipping...", structureName);
          }
          return null;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }
}
