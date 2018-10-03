

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

import java.util.List;

public class CubitToolsOptions {

    public int chunksPerTick;

    private CustomConfig configFile;

    public CubitToolsOptions(CustomConfig configFile) {
        this.configFile = configFile;
        setup();
        this.configFile.saveAndReload();
    }


    private void setup() {
        this.chunksPerTick = (int) this.getObjectValue("regenerator.chunksPerTick", 30);
    }

    public Object getObjectValue(String path, Object defaultValue) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, defaultValue);
        }
        return this.configFile.get(path);

    }

    public Object getStringList(String path, List<String> list) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, list);
        }
        return this.configFile.getStringList(path);
    }


    public CustomConfig getFile() {
        return this.configFile;
    }

}
