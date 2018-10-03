
/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitTools.flatfile;


import de.linzn.cubit.internal.configurations.setup.CustomConfig;
import org.bukkit.plugin.Plugin;

public class CubitToolsConfigHandler {

    public CubitToolsOptions cubitToolsOptions;
    private Plugin plugin;

    public CubitToolsConfigHandler(Plugin plugin) {
        this.plugin = plugin;
        CustomConfig settingsConfig = new CustomConfig(this.plugin, this.plugin.getDataFolder(), "cubitToolsOptions.yml");
        this.cubitToolsOptions = new CubitToolsOptions(settingsConfig);
    }
}
