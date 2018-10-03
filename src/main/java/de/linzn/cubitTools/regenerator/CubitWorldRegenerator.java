/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitTools.regenerator;

import de.linzn.cubitTools.plugin.CubitToolsPlugin;
import de.linzn.cubitTools.regenerator.tasks.CleanupTask;
import de.linzn.cubitTools.regenerator.tasks.RegenerationTask;
import de.linzn.cubitTools.regenerator.utils.DebugLevel;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.World;

import java.util.ArrayList;

public class CubitWorldRegenerator {
    public final ArrayList<Pair<Integer, Integer>> regenerateChunks = new ArrayList<>();
    private CubitToolsPlugin plugin;
    private boolean isRunning = false;

    public CubitWorldRegenerator(CubitToolsPlugin plugin) {
        this.plugin = plugin;
    }


    public void startCleanupWorld(World world) {
        if (isRunning()) {
            if (plugin.debug(DebugLevel.LOW)) {
                plugin.debug("There is already an active cleanup task!");
            }
            return;
        }
        setRunning(true);

        new CleanupTask(this.plugin, this, world).runTaskTimerAsynchronously(this.plugin, 0, 20L);
        new RegenerationTask(this.plugin, this, world).runTaskTimer(this.plugin, 60L, 20L);

        if (plugin.debug(DebugLevel.LOW)) {
            plugin.debug("Starting world cleanup task for " + world.getName());
        }

    }


    public boolean isRunning() {
        return this.isRunning;
    }

    public void setRunning(boolean value) {
        this.isRunning = value;
    }

}
