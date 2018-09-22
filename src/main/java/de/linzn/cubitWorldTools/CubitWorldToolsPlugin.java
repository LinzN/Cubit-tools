/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitWorldTools;


import de.linzn.cubitWorldTools.commands.Tester;
import de.linzn.cubitWorldTools.flatfile.YMLSetup;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;


public class CubitWorldToolsPlugin extends JavaPlugin {

    private static CubitWorldToolsPlugin inst;
    public YMLSetup ymlSetup;

    public static CubitWorldToolsPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        if (!getPluginDepends()) {
            this.setEnabled(false);
            return;
        }
        getCommand("tester").setExecutor(new Tester());
        this.ymlSetup = new YMLSetup(this);
        getLogger().info("Cubit-world-tools hooked!");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(CubitWorldToolsPlugin.inst());
    }

    private boolean getPluginDepends() {
        if (this.getServer().getPluginManager().getPlugin("Cubit") == null) {
            this.getLogger().severe("Error: " + "Cubit not found!");
            return false;
        }

        if (this.getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            this.getLogger().severe("Error: " + "Dynmap not found!");
            return false;
        }

        return true;
    }


}
