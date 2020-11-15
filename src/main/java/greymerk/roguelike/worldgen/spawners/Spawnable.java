package greymerk.roguelike.worldgen.spawners;

import com.google.common.collect.Lists;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;

public class Spawnable {

  private List<SpawnPotential> potentials = Lists.newArrayList();

  public Spawnable(List<SpawnPotential> spawnPotentials) {
    potentials.addAll(spawnPotentials);
  }

  public void generate(WorldEditor editor, Random random, Coord cursor, int level) {
    Coord pos = new Coord(cursor);
    editor.setBlock(pos, new MetaBlock(Blocks.MOB_SPAWNER.getDefaultState()), true, true);

    TileEntity tileentity = editor.getTileEntity(pos);
    if (!(tileentity instanceof TileEntityMobSpawner)) {
      return;
    }

    TileEntityMobSpawner spawner = (TileEntityMobSpawner) tileentity;
    MobSpawnerBaseLogic spawnerLogic = spawner.getSpawnerBaseLogic();

    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("x", pos.getX());
    nbt.setInteger("y", pos.getY());
    nbt.setInteger("z", pos.getZ());

    nbt.setTag("SpawnPotentials", getSpawnPotentials(random, level));

    spawnerLogic.readFromNBT(nbt);
    spawnerLogic.updateSpawner();
    tileentity.markDirty();
  }

  private NBTTagList getSpawnPotentials(Random random, int level) {
    NBTTagList potentialsNbtTagList = new NBTTagList();
    potentials.stream()
        .map(potential -> potential.getSpawnPotentials(random, level))
        .forEach(potentials -> potentials.forEach(potentialsNbtTagList::appendTag));
    return potentialsNbtTagList;
  }
}
