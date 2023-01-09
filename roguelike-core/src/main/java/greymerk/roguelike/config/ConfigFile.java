package greymerk.roguelike.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static greymerk.roguelike.dungeon.Dungeon.MOD_ID;

public class ConfigFile {

  private static final Logger logger = LogManager.getLogger(MOD_ID);

  public static void read(ConfigurationMap configFile, String filename) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(filename))));
      while (true) {
        Configuration config = INIParser.readLine(reader);
        if (config == null) {
          break;
        }
        configFile.put(config.key, config.value);
      }
    } catch (Exception exception) {
      logger.error("Error while reading config file. : ", exception);
    }
  }

  public static void write(ConfigurationMap configFile, String filename) throws Exception {
    FileOutputStream stream = new FileOutputStream(filename, true);

    stream.getChannel().truncate(0);

    BufferedWriter buffered = new BufferedWriter(new OutputStreamWriter(stream));

    for (Configuration configuration : configFile.asList()) {
      INIParser.write(configuration, buffered);
    }

    buffered.close();
  }

}