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

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import de.linzn.cubit.internal.configurations.setup.CustomConfig;
import de.linzn.cubitWorldTools.CubitObject;
import de.linzn.cubitWorldTools.CubitWorldToolsPlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.List;

public class ChunkFlatfile {


    private CustomConfig configFile;

    public ChunkFlatfile(CustomConfig configFile) {
        CubitWorldToolsPlugin plugin = CubitWorldToolsPlugin.inst();
        this.configFile = configFile;
        this.configFile.saveAndReload();

        File cubitStorage = new File(plugin.getDataFolder(), "cubitStorage");
        if (!cubitStorage.exists()) {
            try {
                boolean storage = cubitStorage.mkdirs();
                if (storage) {
                    plugin.getLogger().info("Created cubitStorage directory");
                } else {
                    plugin.getLogger().severe("Error while creating cubitStorage directory");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void addData(CubitObject cubitObject) {
        // Add to index
        this.configFile.getStringList("index").add(cubitObject.name);
        // Add data
        this.configFile.set(cubitObject.name + ".world", cubitObject.world.getName());
        this.configFile.set(cubitObject.name + ".minPoint.cordX", cubitObject.maxPoint.getX());
        this.configFile.set(cubitObject.name + ".minPoint.cordY", cubitObject.maxPoint.getY());
        this.configFile.set(cubitObject.name + ".minPoint.cordZ", cubitObject.maxPoint.getZ());
        this.configFile.set(cubitObject.name + ".maxPoint.cordX", cubitObject.maxPoint.getX());
        this.configFile.set(cubitObject.name + ".maxPoint.cordY", cubitObject.maxPoint.getY());
        this.configFile.set(cubitObject.name + ".maxPoint.cordZ", cubitObject.maxPoint.getZ());
        this.configFile.set(cubitObject.name + ".zeroPoint.cordX", cubitObject.zeroPoint.getX());
        this.configFile.set(cubitObject.name + ".zeroPoint.cordY", cubitObject.zeroPoint.getY());
        this.configFile.set(cubitObject.name + ".zeroPoint.cordZ", cubitObject.zeroPoint.getZ());
        this.configFile.saveAndReload();
    }

    public CubitObject getData(String name) {
        BukkitWorld world = new BukkitWorld(Bukkit.getWorld(this.configFile.getString(name + ".world")));
        double minX = this.configFile.getDouble(name + ".minPoint.cordX");
        double minY = this.configFile.getDouble(name + ".minPoint.cordX");
        double minZ = this.configFile.getDouble(name + ".minPoint.cordX");
        Vector minPoint = new Vector(minX, minY, minZ);
        double maxX = this.configFile.getDouble(name + ".maxPoint.cordX");
        double maxY = this.configFile.getDouble(name + ".maxPoint.cordX");
        double maxZ = this.configFile.getDouble(name + ".maxPoint.cordX");
        Vector maxPoint = new Vector(maxX, maxY, maxZ);
        double zeroX = this.configFile.getDouble(name + ".zeroPoint.cordX");
        double zeroY = this.configFile.getDouble(name + ".zeroPoint.cordX");
        double zeroZ = this.configFile.getDouble(name + ".zeroPoint.cordX");
        Vector zeroPoint = new Vector(zeroX, zeroY, zeroZ);
        return new CubitObject(name, world, minPoint, maxPoint, zeroPoint);
    }

    public boolean hasData(String name) {
        return this.configFile.getStringList("index").contains(name);
    }

    public void delData(String name) {
        // Remove data
        this.configFile.set(name + ".world", null);
        this.configFile.set(name + ".minPoint.cordX", null);
        this.configFile.set(name + ".minPoint.cordY", null);
        this.configFile.set(name + ".minPoint.cordZ", null);
        this.configFile.set(name + ".maxPoint.cordX", null);
        this.configFile.set(name + ".maxPoint.cordY", null);
        this.configFile.set(name + ".maxPoint.cordZ", null);
        this.configFile.set(name + ".zeroPoint.cordX", null);
        this.configFile.set(name + ".zeroPoint.cordY", null);
        this.configFile.set(name + ".zeroPoint.cordZ", null);
        // Remove index entry
        this.configFile.getStringList("index").remove(name);
        this.configFile.saveAndReload();
    }

    public List<String> getIndex() {
        return this.configFile.getStringList("index");
    }


    public CustomConfig getFile() {
        return this.configFile;
    }

}
