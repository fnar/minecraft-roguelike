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
import java.util.Optional;
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
  public static final RogueConfig DIMENSIONBL = new RogueConfig("dimensionBL", new Integer[]{});
  public static final RogueConfig DIMENSIONWL = new RogueConfig("dimensionWL", new Integer[]{0});
  public static final RogueConfig DONATURALSPAWN = new RogueConfig("doNaturalSpawn", true);
  public static final RogueConfig ENCASE = new RogueConfig("encase", false);
  public static final RogueConfig FURNITURE = new RogueConfig("furniture", true);
  public static final RogueConfig GENEROUS = new RogueConfig("generous", true);
  public static final RogueConfig LOOTING = new RogueConfig("looting", 0.085D);
  public static final RogueConfig LOWERLIMIT = new RogueConfig("lowerLimit", 60);
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
  public static final RogueConfig MOBS_ITEMS_DROP_CHANCE = new RogueConfig("mobs.items.dropChance", 0.0);
  public static final RogueConfig MOBS_ITEMS_ENCHANTED_CHANCE = new RogueConfig("mobs.items.enchanted.chance", new Double[]{-1.0, -1.0, -1.0, -1.0, -1.0});
  public static final RogueConfig MOBS_ITEMS_ENCHANTMENTS_LEVELS = new RogueConfig("overrideMobEquipmentEnchantmentLevels", new Integer[]{-1, -1, -1, -1, -1});

  private static final boolean DEFAULT_BOOLEAN = false;
  private static final String DEFAULT_STRING_VALUE = "";
  private static final int DEFAULT_INT = 0;
  private static final double DEFAULT_DOUBLE = 0.0;
  private static final List<Integer> DEFAULT_INT_LIST = Collections.unmodifiableList(Lists.newArrayList());
  private static final List<Double> DEFAULT_DOUBLE_LIST = Collections.unmodifiableList(Lists.newArrayList());

  private final String name;
  private final String stringValue;
  private final Boolean booleanValue;
  private final Integer intValue;
  private final Double doubleValue;
  private final List<Integer> intsValue;
  private final List<Double> doublesValue;

  RogueConfig(String name, boolean value) {
    this(name, DEFAULT_STRING_VALUE, value, DEFAULT_INT, DEFAULT_DOUBLE, DEFAULT_INT_LIST, DEFAULT_DOUBLE_LIST);
  }

  RogueConfig(String name, String value) {
    this(name, value, DEFAULT_BOOLEAN, DEFAULT_INT, DEFAULT_DOUBLE, DEFAULT_INT_LIST, DEFAULT_DOUBLE_LIST);
  }

  RogueConfig(String name, int value) {
    this(name, DEFAULT_STRING_VALUE, DEFAULT_BOOLEAN, value, DEFAULT_DOUBLE, DEFAULT_INT_LIST, DEFAULT_DOUBLE_LIST);
  }

  RogueConfig(String name, double value) {
    this(name, DEFAULT_STRING_VALUE, DEFAULT_BOOLEAN, DEFAULT_INT, value, DEFAULT_INT_LIST, DEFAULT_DOUBLE_LIST);
  }

  RogueConfig(String name, Integer[] value) {
    this(name, DEFAULT_STRING_VALUE, DEFAULT_BOOLEAN, DEFAULT_INT, DEFAULT_DOUBLE, Lists.newArrayList(value), DEFAULT_DOUBLE_LIST);
  }

  RogueConfig(String name, Double[] value) {
    this(name, DEFAULT_STRING_VALUE, DEFAULT_BOOLEAN, DEFAULT_INT, DEFAULT_DOUBLE, DEFAULT_INT_LIST, Lists.newArrayList(value));
  }

  RogueConfig(
      String name,
      String stringValue,
      Boolean booleanValue,
      Integer intValue,
      Double doubleValue,
      List<Integer> intsValue,
      List<Double> doublesValue
  ) {
    this.name = name;
    this.stringValue = stringValue;
    this.booleanValue = booleanValue;
    this.intValue = intValue;
    this.doubleValue = doubleValue;
    this.intsValue = intsValue;
    this.doublesValue = doublesValue;
  }

  public String getName() {
    return name;
  }

  @SuppressWarnings("unchecked")
  private static void setDefaults() {
    if (!instance.ContainsKey(BREAK_IF_REQUIRED_MOD_IS_MISSING.name)) {
      BREAK_IF_REQUIRED_MOD_IS_MISSING.setBoolean(BREAK_IF_REQUIRED_MOD_IS_MISSING.booleanValue);
    }

    if (!instance.ContainsKey(DONATURALSPAWN.name)) {
      DONATURALSPAWN.setBoolean(DONATURALSPAWN.booleanValue);
    }
    if (!instance.ContainsKey(SPAWNFREQUENCY.name)) {
      SPAWNFREQUENCY.setInt(SPAWNFREQUENCY.intValue);
    }
    if (!instance.ContainsKey(SPAWNCHANCE.name)) {
      SPAWNCHANCE.setDouble(SPAWNCHANCE.doubleValue);
    }
    if (!instance.ContainsKey(GENEROUS.name)) {
      GENEROUS.setBoolean(GENEROUS.booleanValue);
    }
    if (!instance.ContainsKey(DIMENSIONWL.name)) {
      DIMENSIONWL.setIntList(DIMENSIONWL.intsValue);
    }
    if (!instance.ContainsKey(DIMENSIONBL.name)) {
      DIMENSIONBL.setIntList(DIMENSIONBL.intsValue);
    }
    if (!instance.ContainsKey(PRECIOUSBLOCKS.name)) {
      PRECIOUSBLOCKS.setBoolean(PRECIOUSBLOCKS.booleanValue);
    }
    if (!instance.ContainsKey(LOOTING.name)) {
      LOOTING.setDouble(LOOTING.doubleValue);
    }
    if (!instance.ContainsKey(UPPERLIMIT.name)) {
      UPPERLIMIT.setInt(UPPERLIMIT.intValue);
    }
    if (!instance.ContainsKey(LOWERLIMIT.name)) {
      LOWERLIMIT.setInt(LOWERLIMIT.intValue);
    }
    if (!instance.ContainsKey(ROGUESPAWNERS.name)) {
      ROGUESPAWNERS.setBoolean(ROGUESPAWNERS.booleanValue);
    }
    if (!instance.ContainsKey(ENCASE.name)) {
      ENCASE.setBoolean(ENCASE.booleanValue);
    }
    if (!instance.ContainsKey(FURNITURE.name)) {
      FURNITURE.setBoolean(FURNITURE.booleanValue);
    }
    if (!instance.ContainsKey(RANDOM.name)) {
      RANDOM.setBoolean(RANDOM.booleanValue);
    }
    if (!instance.ContainsKey(SPAWNBUILTIN.name)) {
      SPAWNBUILTIN.setBoolean(SPAWNBUILTIN.booleanValue);
    }
    if (!instance.ContainsKey(SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.name)) {
      SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.setInt(SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.intValue);
    }
    if (!instance.ContainsKey(SPAWN_ATTEMPTS.name)) {
      SPAWN_ATTEMPTS.setInt(SPAWN_ATTEMPTS.intValue);
    }

    if (!instance.ContainsKey(VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM.getName())) {
      VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM.setString(VANILLA_STRUCTURES_TO_CHECK_MINIMUM_DISTANCE_FROM.stringValue);
    }

    if (!instance.ContainsKey(MOBS_ITEMS_ENCHANTMENTS_LEVELS.getName())) {
      MOBS_ITEMS_ENCHANTMENTS_LEVELS.setIntList(MOBS_ITEMS_ENCHANTMENTS_LEVELS.intsValue);
    }
    if (!instance.ContainsKey(MOBS_ITEMS_ENCHANTED_CHANCE.getName())) {
      MOBS_ITEMS_ENCHANTED_CHANCE.setDoubleList(MOBS_ITEMS_ENCHANTED_CHANCE.doublesValue);
    }
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

  public String getString() {
    if (testing) {
      return stringValue;
    }
    return instance.GetString(name, stringValue);
  }

  private void setString(String defaultStringValue) {
    reload(false);
    instance.set(name, defaultStringValue);
  }

  public boolean getBoolean() {
    if (testing) {
      return booleanValue;
    }
    reload(false);
    return instance.GetBoolean(name, booleanValue);
  }

  public void setBoolean(Boolean value) {
    reload(false);
    instance.set(name, value);
  }

  public int getInt() {
    if (testing) {
      return intValue;
    }
    reload(false);
    return instance.GetInteger(name, intValue);
  }

  public void setInt(int value) {
    reload(false);
    instance.set(name, value);
  }

  public double getDouble() {
    if (testing) {
      return doubleValue;
    }
    reload(false);
    return instance.GetDouble(name, doubleValue);
  }

  public void setDouble(double value) {
    reload(false);
    instance.set(name, value);
  }

  @SuppressWarnings("unchecked")
  public List<Integer> getIntList() {
    if (testing) {
      return intsValue;
    }
    reload(false);
    return instance.getIntegers(name, intsValue);
  }

  public void setIntList(List<Integer> value) {
    reload(false);
    instance.set(name, value);
  }

  public Optional<Integer> getIntAtIndex(int index) {
    List<Integer> values = getIntList();
    if (index >= values.size()) {
      return Optional.empty();
    }
    return Optional.of(values.get(index));
  }

  @SuppressWarnings("unchecked")
  public List<Double> getDoubleList() {
    if (testing) {
      return doublesValue;
    }
    reload(false);
    return instance.getDoubles(name, doublesValue);
  }

  public void setDoubleList(List<Double> value) {
    reload(false);
    instance.set(name, value.toArray(new Double[]{}));
  }

  public Optional<Double> getDoubleAtIndex(int index) {
    List<Double> values = getDoubleList();
    if (index >= values.size()) {
      return Optional.empty();
    }
    return Optional.of(values.get(index));
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
