package greymerk.roguelike.config;


import java.io.BufferedReader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class INIParser {

  private static final Pattern LINE_PATTERN = Pattern.compile("^([^=]+)=(.*)$");
  private static final Pattern COMMENT_PATTERN = Pattern.compile("^#");

  public static Configuration readLine(BufferedReader reader) throws Exception {
    while (true) {
      String line = reader.readLine();

      if (line == null) {
        return null;
      }

      line = line.trim();

      if ((line.length() == 0) || COMMENT_PATTERN.matcher(line).find()) {
        continue;
      }

      Matcher matcher = LINE_PATTERN.matcher(line);

      if (!matcher.find()) {
        continue;
      }

      String key = matcher.group(1).trim();

      if (key.length() == 0) {
        continue;
      }

      String value = matcher.group(2).trim();

      if (value.length() == 0) {
        continue;
      }

      return new Configuration(key, value);
    }

  }

  public static void write(Configuration config, Writer writer) throws Exception {
    writer.write(config.key);
    writer.write("=");
    writer.write(config.value);
    writer.write(System.getProperty("line.separator"));
  }

}
