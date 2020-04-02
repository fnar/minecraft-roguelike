package greymerk.roguelike.worldgen.spawners;

import com.google.gson.JsonObject;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;

class SpawnPotentialParser {

  public static SpawnPotential parse(JsonObject entry) throws Exception {
    int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;
    if (!entry.has("name")) {
      throw new Exception("Spawn potential missing name");
    }

    String name = entry.get("name").getAsString();
    boolean equip = entry.has("equip") && entry.get("equip").getAsBoolean();

    NBTTagCompound nbt = entry.has("nbt")
        ? JsonToNBT.getTagFromJson(entry.get("nbt").getAsString())
        : new NBTTagCompound();
    return new SpawnPotential(name, equip, weight, nbt);
  }
}
