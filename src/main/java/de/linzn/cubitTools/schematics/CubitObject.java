/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitTools.schematics;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Chunk;

public class CubitObject {
    public String name;
    public BukkitWorld world;
    public BlockVector3 minPoint;
    public BlockVector3 maxPoint;
    public BlockVector3 originPoint;

    public CubitObject(String name, Chunk chunk) {
        this.name = name;
        this.world = new BukkitWorld(chunk.getWorld());
        this.minPoint = BlockVector3.at(chunk.getX() * 16, 0, chunk.getZ() * 16);
        this.maxPoint = BlockVector3.at(chunk.getX() * 16 + 15, 256, chunk.getZ() * 16 + 15);
        this.originPoint = BlockVector3.at(chunk.getX() * 16, 0, chunk.getZ() * 16);
    }

    public CubitObject(String name, BukkitWorld world, BlockVector3 minPoint, BlockVector3 maxPoint, BlockVector3 originPoint) {
        this.name = name;
        this.world = world;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.originPoint = originPoint;
    }
}
