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
import de.linzn.cubitWorldTools.CubitObject;

public class ChunkFlatfile {


    private CustomConfig configFile;

    public ChunkFlatfile(CustomConfig configFile) {
        this.configFile = configFile;
        this.configFile.saveAndReload();
    }

    public void addData(CubitObject cubitObject) {

    }

    public CubitObject getData() {
        return null;
    }

    public void delData() {

    }


    public CustomConfig getFile() {
        return this.configFile;
    }

}
