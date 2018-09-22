/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitWorldTools.fawe;

import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import de.linzn.cubitWorldTools.CubitObject;
import de.linzn.cubitWorldTools.CubitWorldToolsPlugin;

import java.io.File;
import java.io.IOException;

public class SaveData {
    private CubitObject cubitObject;
    private File cubitFile;
    private boolean override;

    public SaveData(File cubitFile, CubitObject cubitObject, boolean override) {
        this.cubitObject = cubitObject;
        this.cubitFile = cubitFile;
        this.override = override;
    }

    public boolean verify() {
        if (cubitFile.exists()) {
            if (this.override) {
                CubitWorldToolsPlugin.inst().getLogger().warning("File exists already (Override): " + cubitFile.getName());
                return true;
            } else {
                CubitWorldToolsPlugin.inst().getLogger().warning("File exists already (Error): " + cubitFile.getName());
                return false;
            }
        }
        return true;
    }


    public boolean save() {
        try {
            CuboidRegion cuboidRegion = new CuboidRegion(this.cubitObject.world, this.cubitObject.minPoint, this.cubitObject.maxPoint);
            Schematic schematic = new Schematic(cuboidRegion);
            schematic.save(cubitFile, ClipboardFormat.SCHEMATIC);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
