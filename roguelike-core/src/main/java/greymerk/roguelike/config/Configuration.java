package greymerk.roguelike.config;


public class Configuration {

  public String key;
  public String value;

  public Configuration(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}