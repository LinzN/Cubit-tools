/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitTools.plugin;


import de.linzn.cubitTools.CToolsCommand;
import de.linzn.cubitTools.flatfile.CubitToolsConfigHandler;
import de.linzn.cubitTools.regenerator.CubitWorldRegenerator;
import de.linzn.cubitTools.regenerator.utils.DebugLevel;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;


public class CubitToolsPlugin extends JavaPlugin {

    private static CubitToolsPlugin inst;
    private CubitToolsConfigHandler cubitToolsConfigHandler;
    private DebugLevel debugLevel;
    private CubitWorldRegenerator cubitWorldRegenerator;


    public static CubitToolsPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        if (!getPluginDepends()) {
            this.setEnabled(false);
            return;
        }
        this.debugLevel = DebugLevel.HIGH;
        this.cubitToolsConfigHandler = new CubitToolsConfigHandler(this);
        this.cubitWorldRegenerator = new CubitWorldRegenerator(this);
        getCommand("ctools").setExecutor(new CToolsCommand(this, this.cubitWorldRegenerator));
        debug("cubit-tools hooked!");
    }

    @Override
    public void onDisable() {
        this.cubitWorldRegenerator.setRunning(false);
        getServer().getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
    }

    private boolean getPluginDepends() {
        if (this.getServer().getPluginManager().getPlugin("Cubit") == null) {
            debug("Error: " + "Cubit not found!");
            return false;
        }

        if (this.getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            debug("Error: " + "WorldEdit not found!");
            return false;
        }
        if (this.getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            debug("Error: " + "WorldGuard not found!");
            return false;
        }

        return true;
    }

    public CubitToolsConfigHandler getConfigHandler() {
        return cubitToolsConfigHandler;
    }

    public boolean debug(DebugLevel level) {
        return debugLevel.ordinal() >= level.ordinal();
    }

    public void debug(String message) {
        getLogger().info(message);
    }

}
