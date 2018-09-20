/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitWorldTools.flatfile;


import de.linzn.cubit.internal.configurations.setup.CustomConfig;
import org.bukkit.plugin.Plugin;

public class YMLSetup {

    public ChunkFlatfile chunkFlatfile;
    private Plugin plugin;

    public YMLSetup(Plugin plugin) {
        this.plugin = plugin;
        CustomConfig settingsConfig = new CustomConfig(this.plugin, this.plugin.getDataFolder(), "chunkFlatfile.yml");
        this.chunkFlatfile = new ChunkFlatfile(settingsConfig);
    }
}
