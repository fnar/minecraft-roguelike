package greymerk.roguelike.config;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConfigurationMap {

  private static final String true_regex = "^\\s*(?:t(?:rue)?|y(?:es)?)\\s*$";
  private static final String false_regex = "^\\s*(?:f(?:alse)?|no?)\\s*$";
  private static final String true_string = "true";
  private static final String false_string = "false";
  protected Map<String, String> configurationsByName = new HashMap<>();
  private final Pattern true_pattern = Pattern.compile(true_regex);
  private final Pattern false_pattern = Pattern.compile(false_regex);

  public boolean ContainsKey(String key) {
    if (key == null) {
      return false;
    }

    return configurationsByName.get(key) != null;
  }

  public String Get(String key) {

    if (key == null) {
      return null;
    }

    return configurationsByName.get(key);
  }

  public String Get(String key, String fallback) {

    String value = Get(key);

    return (value == null) ? fallback : value;

  }

  public double GetDouble(String key, double fallback) {

    String value = Get(key);

    if (value == null) {
      return fallback;
    }

    try {

      return Double.parseDouble(value);

    } catch (NumberFormatException ignored) {
    }

    return fallback;

  }

  public int GetInteger(String key, int fallback) {

    String value = Get(key);

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

    String value = Get(key);

    if (value == null) {
      return fallback;
    }

    if (true_pattern.matcher(value).find()) {
      return true;
    }

    if (false_pattern.matcher(value).find()) {
      return false;
    }

    return fallback;

  }

  public List<Integer> getIntegers(String key, List<Integer> fallback) {

    String value = Get(key);

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

    String value = Get(key);

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


  public void set(String key, String value) {
    if (key == null) {
      return;
    }

    if (value == null) {

      configurationsByName.remove(key);

      return;
    }

    configurationsByName.put(key, value);

  }

  public void set(String key, double value) {
    set(key, Double.toString(value));
  }

  public void set(String key, int value) {
    set(key, Integer.toString(value));

  }

  public void set(String key, boolean value) {
    set(key, value ? true_string : false_string);
  }

  public void set(String key, List<Integer> value) {
    set(key, StringUtils.join(value, ","));
  }

  public void set(String key, Double[] value) {
    set(key, StringUtils.join(value, ","));
  }

  public void Unset(String key) {
    if (key == null) {
      return;
    }

    configurationsByName.remove(key);
  }

  public String GetString(String name, String defaultValue) {
    return Optional.ofNullable(Get(name, defaultValue))
        .orElse(defaultValue);
  }

  public List<Configuration> asList() {
    return configurationsByName.entrySet().stream()
        .sorted(Entry.comparingByKey())
        .map(entry -> new Configuration(entry.getKey(), entry.getValue()))
        .collect(Collectors.toList());
  }

}
