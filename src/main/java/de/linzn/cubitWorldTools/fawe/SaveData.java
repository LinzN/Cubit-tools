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
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import de.linzn.cubitWorldTools.CubitWorldToolsPlugin;

import java.io.File;
import java.io.IOException;

public class SaveData {
    private BukkitWorld world;
    private File cubitFile;
    private Vector minPoint;
    private Vector maxPoint;
    private boolean override;

    public SaveData(File cubitFile, BukkitWorld world, Vector minPoint, Vector maxPoint, boolean override) {
        this.cubitFile = cubitFile;
        this.world = world;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
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
            CuboidRegion region = new CuboidRegion(world, minPoint, maxPoint);
            Schematic schem = new Schematic(region);
            schem.save(cubitFile, ClipboardFormat.SCHEMATIC);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
