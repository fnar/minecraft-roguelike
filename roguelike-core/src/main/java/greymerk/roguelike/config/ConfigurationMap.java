package greymerk.roguelike.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConfigurationMap {

  private static final Pattern TRUE_PATTERN = Pattern.compile("^\\s*(?:t(?:rue)?|y(?:es)?)\\s*$");
  private static final Pattern FALSE_PATTERN = Pattern.compile("^\\s*(?:f(?:alse)?|no?)\\s*$");

  protected Map<String, Configuration> configurationsByName = new HashMap<>();

  public void put(String key, String value) {
    configurationsByName.put(key, new Configuration(key, value));
  }

  public boolean containsKey(String key) {
    return configurationsByName.containsKey(key);
  }

  public String get(String key) {
    if (key == null) {
      return null;
    }

    return configurationsByName.get(key).getValue();
  }

  public String get(String key, String fallback) {

    String value = get(key);

    return (value == null) ? fallback : value;

  }

  public double getDouble(String key, double fallback) {

    String value = get(key);

    if (value == null) {
      return fallback;
    }

    try {

      return Double.parseDouble(value);

    } catch (NumberFormatException ignored) {
    }

    return fallback;

  }

  public int getInteger(String key, int fallback) {
    String value = get(key);

    if (value == null) {
      return fallback;
    }

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
    }
    return fallback;
  }

  public boolean GetBoolean(String key, boolean fallback) {

    String value = get(key);

    if (value == null) {
      return fallback;
    }

    if (TRUE_PATTERN.matcher(value).find()) {
      return true;
    }

    if (FALSE_PATTERN.matcher(value).find()) {
      return false;
    }

    return fallback;

  }

  public List<Integer> getIntegers(String key, List<Integer> fallback) {

    String value = get(key);

    if (value == null) {
      return fallback;
    }

    String[] values = value.split(",");

    List<Integer> ints = new ArrayList<>();

    for (String s : values) {
      try {
        ints.add(Integer.parseInt(s));
      } catch (NumberFormatException ignored) {
      }
    }

    return ints;
  }

  public List<Double> getDoubles(String key, List<Double> fallback) {

    String value = get(key);

    if (value == null) {
      return fallback;
    }

    String[] values = value.split(",");

    List<Double> ints = new ArrayList<>();

    for (String s : values) {
      try {
        ints.add(Double.parseDouble(s));
      } catch (NumberFormatException ignored) {
      }
    }

    return ints;
  }

  public List<Configuration> asList() {
    return configurationsByName.values().stream().sorted().collect(Collectors.toList());
  }

}
