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

import de.linzn.cubitTools.plugin.CubitToolsPlugin;
import de.linzn.cubitTools.regenerator.CubitWorldRegenerator;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.scheduler.BukkitRunnable;


public class RegenerationTask extends BukkitRunnable {

    private final CubitToolsPlugin plugin;
    private final CubitWorldRegenerator regenerator;
    private final World world;
    private int i = 0;

    public RegenerationTask(CubitToolsPlugin plugin, CubitWorldRegenerator regenerator, World world) {
        this.plugin = plugin;
        this.regenerator = regenerator;
        this.world = world;
    }


    @Override
    public void run() {
        if (!this.regenerator.isRunning()) {
            this.cancel();
            return;
        }

        if (i >= 2) {
            plugin.debug("Chunks in queue: " + this.regenerator.regenerateChunks.size());
            i = 0;
        }

        int chunksPerTick = this.plugin.getConfigHandler().cubitToolsOptions.chunksPerTick;
        for (int i = 0; i < chunksPerTick; i++) {
            if (this.regenerator.regenerateChunks.isEmpty()) {
                continue;
            }

            Pair<Integer, Integer> chunk = this.regenerator.regenerateChunks.iterator().next();
            if (!world.regenerateChunk(chunk.getLeft(), chunk.getRight())) {
                plugin.debug("Error while regenerating chunk: " + chunk.getLeft() + ", " + chunk.getRight());
            }
            this.regenerator.regenerateChunks.remove(chunk);
        }
        i++;
    }
}
