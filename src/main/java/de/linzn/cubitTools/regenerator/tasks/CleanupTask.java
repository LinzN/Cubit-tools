/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitTools.regenerator.tasks;


import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import de.linzn.cubitTools.plugin.CubitToolsPlugin;
import de.linzn.cubitTools.regenerator.CubitWorldRegenerator;
import de.linzn.cubitTools.regenerator.utils.CordConverter;
import de.linzn.cubitTools.regenerator.utils.DebugLevel;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;


public class CleanupTask extends BukkitRunnable {

    private static final String STATS_FORMAT = "%s: %s/%s, deleted %s regions, %s chunks";

    private final CubitToolsPlugin plugin;
    private final CubitWorldRegenerator regenerator;
    private final World world;
    private final File regionDir;
    private final String[] regionFileNames;
    private final int chunksPerCheck;
    private final ArrayList<Pair<Integer, Integer>> regionChunks = new ArrayList<>();


    private int count = 0, regionChunkX, regionChunkZ, localChunkX = 0, localChunkZ = 0, regionsDeleted = 0, chunksDeleted = 0;


    public CleanupTask(CubitToolsPlugin plugin, CubitWorldRegenerator regenerator, World world) {
        this.plugin = plugin;
        this.regenerator = regenerator;
        this.world = world;
        this.regionDir = this.findRegionFolder();
        this.regionFileNames = this.regionDir.list((dir, name) -> name.matches("r\\.-?\\d+\\.-?\\d+\\.mca"));
        this.chunksPerCheck = 200;

        if (regionFileNames.length > 0) {
            Pair<Integer, Integer> regionLowestChunk = CordConverter.getRegionChunkCoords(regionFileNames[0]);
            regionChunkX = regionLowestChunk.getLeft();
            regionChunkZ = regionLowestChunk.getRight();
        }
    }


    private File findRegionFolder() {
        File worldDir = this.world.getWorldFolder();
        File[] subdir = worldDir.listFiles(file -> file.isDirectory() && (file.getName().startsWith("DIM") || file.getName().equals("region")));

        for (File file : subdir) {
            if (file.getName().equals("region")) {
                return file;
            }
        }

        for (File file : subdir) {
            File[] subsubdir = file.listFiles(file1 -> file1.isDirectory() && file1.getName().equals("region"));
            if (subsubdir.length > 0) {
                return subsubdir[0];
            }
        }

        throw new IllegalStateException("Unable to find region folder for world " + world.getName());
    }

    @Override
    public void run() {

        if (!this.regenerator.isRunning()) {
            plugin.getLogger().info("Cleanup task canceled for " + getRunStats());
            this.cancel();
            return;
        }

        if (count >= regionFileNames.length) {
            if (this.regenerator.regenerateChunks.isEmpty()) {
                plugin.getLogger().info("Cleanup task complete for " + getRunStats());
                this.regenerator.setRunning(false);
                this.cancel();
            }
            return;
        }


        for (int i = 0; i < chunksPerCheck && count < regionFileNames.length; ++i) {
            if (chunksPerCheck <= 1024 && i > 0 && localChunkZ >= 32) {
                return;
            }
            checkNextChunk();
            if (chunksPerCheck <= 1024 && localChunkX == 0 && localChunkZ == 0) {
                return;
            }
        }
    }


    private void checkNextChunk() {
        if (count >= regionFileNames.length) {
            return;
        }

        if (localChunkX >= 32) {
            localChunkX = 0;
            localChunkZ++;
        }

        if (localChunkZ >= 32) {
            checkingRegionFiles();
            if (chunksPerCheck <= 1024) {
                return;
            }
        }

        int chunkX = regionChunkX + localChunkX;
        int chunkZ = regionChunkZ + localChunkZ;

        if (!isProtected(world, chunkX, chunkZ)) {
            regionChunks.add(new ImmutablePair<>(localChunkX, localChunkZ));
        } else {
            if (this.plugin.debug(DebugLevel.HIGH)) {
                this.plugin.debug("Chunk " + getChunkPath(chunkX, chunkZ) + " contains cubit region!");
            }
        }

        localChunkX++;
    }

    private void checkingRegionFiles() {
        ListIterator<Pair<Integer, Integer>> iterator = regionChunks.listIterator();
        boolean containsLoaded = false;
        while (iterator.hasNext()) {
            Pair<Integer, Integer> chunkCoords = iterator.next();
            if (world.isChunkLoaded(regionChunkX + chunkCoords.getLeft(), regionChunkZ + chunkCoords.getRight())) {
                containsLoaded = true;
            }
        }

        if (regionChunks.size() == 1024) {
            if (containsLoaded) {
                addToChunkRegeneration(iterator, world);
            } else {
                deleteWholeRegionFile(iterator);
            }
        } else if (regionChunks.size() > 0) {
            addToChunkRegeneration(iterator, world);
        }

        regionChunks.clear();
        count++;
        if (plugin.debug(DebugLevel.LOW) && count % 20 == 0 && count > 0) {
            plugin.debug(getRunStats());
        }
        if (count < regionFileNames.length) {
            localChunkX = 0;
            localChunkZ = 0;
            Pair<Integer, Integer> regionLowestChunk = CordConverter.getRegionChunkCoords(regionFileNames[count]);
            regionChunkX = regionLowestChunk.getLeft();
            regionChunkZ = regionLowestChunk.getRight();

            if (plugin.debug(DebugLevel.HIGH)) {
                plugin.debug(String.format("Inspecting %s:%s (%s/%s)", world.getName(),
                        regionFileNames[count], count, regionFileNames.length));
            }
        }
    }


    private void deleteWholeRegionFile(ListIterator<Pair<Integer, Integer>> iterator) {
        String regionFileName = regionFileNames[count];
        File regionFile = new File(regionDir, regionFileName);
        if (regionFile.exists() && regionFile.delete()) {
            regionsDeleted++;
            if (plugin.debug(DebugLevel.MEDIUM)) {
                plugin.debug(regionFileName + " deleted from " + world.getName());
            }
            while (iterator.hasPrevious()) {
                iterator.previous();
            }
        } else if (plugin.debug(DebugLevel.MEDIUM)) {
            plugin.debug(String.format("Unable to delete %s from %s",
                    regionFileName, world.getName()));
        }
    }


    private void addToChunkRegeneration(ListIterator<Pair<Integer, Integer>> iterator, World world) {
        String regionFileName = regionFileNames[count];
        int chunkCounter = 0;
        while (iterator.hasPrevious()) {
            Pair<Integer, Integer> localChunkCords = iterator.previous();
            int chunkX = regionChunkX + localChunkCords.getLeft();
            int chunkZ = regionChunkZ + localChunkCords.getRight();

            Pair<Integer, Integer> chunkCord = new ImmutablePair<>(chunkX, chunkZ);
            this.regenerator.regenerateChunks.add(chunkCord);
            plugin.debug("Add queue: " + chunkCord.getLeft() + ", " + chunkCord.getRight() + ": " + regionFileName + ": " + world.getName());
            ++chunkCounter;
        }
        chunksDeleted += chunkCounter;
        plugin.debug("Added to queue: " + chunkCounter);
    }


    private String getRunStats() {
        return String.format(STATS_FORMAT, world.getName(), count, regionFileNames.length, regionsDeleted, chunksDeleted);
    }

    private Boolean isProtected(World world, int chunkX, int chunkZ) {
        int chunkBlockX = CordConverter.chunkToBlock(chunkX);
        int chunkBlockZ = CordConverter.chunkToBlock(chunkZ);
        BlockVector3 bottom = BlockVector3.at(chunkBlockX, 0, chunkBlockZ);
        BlockVector3 top = BlockVector3.at(chunkBlockX + 15, 255, chunkBlockZ + 15);
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(world))
                .getApplicableRegions(new ProtectedCuboidRegion("CUBIT_TMP", bottom, top)).size() > 0;
    }

    private String getChunkPath(int chunkX, int chunkZ) {
        return String.valueOf(chunkX) + '_' + chunkZ;
    }


}
