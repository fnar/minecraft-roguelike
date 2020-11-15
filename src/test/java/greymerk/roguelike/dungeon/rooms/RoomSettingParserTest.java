package greymerk.roguelike.dungeon.rooms;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomSettingParserTest {

  @Test
  public void parse_CanParseTheSpawnerId() {
    String roomSettingJson = "{\n" +
        "  \"type\": \"FORTRESS\",\n" +
        "  \"spawnerId\": \"BLAZE\"\n" +
        "}";

    assertThat(parseRoomSetting(roomSettingJson).getSpawnerId()).isEqualTo("BLAZE");
  }

  private RoomSetting parseRoomSetting(String roomSettingJson) {
    JsonObject roomSettingJsonObject = new JsonParser().parse(roomSettingJson).getAsJsonObject();
    return RoomSettingParser.parse(roomSettingJsonObject);
  }

  @Test
  public void parse_parsesTheTreasureChestType_WithAValidValue() {
    String roomSettingJson = "{\n" +
        "  \"type\": \"FORTRESS\",\n" +
        "  \"treasureType\": \"ARMOUR\"\n" +
        "}";
    assertThat(parseRoomSetting(roomSettingJson).getTreasureType()).isEqualTo("ARMOUR");
  }

  @Test
  public void parse_parseTheTreasureChestTypeAsNull_WhenTheKeyIsAbsent() {
    String roomSettingJson = "{\n" +
        "  \"type\": \"FORTRESS\"\n" +
        "}";
    assertThat(parseRoomSetting(roomSettingJson).getTreasureType()).isNull();
  }

  @Test
  public void parse_parseTheTreasureChestTypeAsNull_WhenNull() {
    String roomSettingJson = "{\n" +
        "  \"type\": \"FORTRESS\",\n" +
        "  \"treasureType\": null\n" +
        "}";
    assertThat(parseRoomSetting(roomSettingJson).getTreasureType()).isNull();
  }
}