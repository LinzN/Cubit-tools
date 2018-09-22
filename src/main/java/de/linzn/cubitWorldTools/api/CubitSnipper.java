/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitWorldTools.api;

import de.linzn.cubitWorldTools.CubitObject;
import de.linzn.cubitWorldTools.CubitWorldToolsPlugin;
import de.linzn.cubitWorldTools.fawe.SaveData;
import org.bukkit.Chunk;

import java.io.File;

public class CubitSnipper {

    public static void saveCubitRegion(String name, Chunk chunk, boolean override) {

        CubitObject cubitObject = new CubitObject(name, chunk);

        boolean hasData = CubitWorldToolsPlugin.inst().ymlSetup.chunkFlatfile.hasData(cubitObject.name);
        if (hasData && !override) {
            return;
        }
        File cubitStorage = new File(CubitWorldToolsPlugin.inst().getDataFolder(), "cubitStorage");

        SaveData saveData = new SaveData(new File(cubitStorage, cubitObject.name + ".cubit"), cubitObject, override);
        if (!saveData.verify()) {
            return;
        }
        saveData.save();
        if (!saveData.save()) {
            CubitWorldToolsPlugin.inst().getLogger().warning("Error on saving file!");
        }
        CubitWorldToolsPlugin.inst().ymlSetup.chunkFlatfile.addData(cubitObject);
    }

    public static void pasteCubitRegion(String name) {
        boolean hasData = CubitWorldToolsPlugin.inst().ymlSetup.chunkFlatfile.hasData(name);
        if (!hasData) {
            return;
        }
    }

}
