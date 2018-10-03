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

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import org.bukkit.Chunk;

public class CubitObject {
    public String name;
    public BukkitWorld world;
    public Vector minPoint;
    public Vector maxPoint;
    public Vector originPoint;

    public CubitObject(String name, Chunk chunk) {
        this.name = name;
        this.world = new BukkitWorld(chunk.getWorld());
        this.minPoint = new Vector(chunk.getX() * 16, 0, chunk.getZ() * 16);
        this.maxPoint = new Vector(chunk.getX() * 16 + 15, 256, chunk.getZ() * 16 + 15);
        this.originPoint = new Vector(chunk.getX() * 16, 0, chunk.getZ() * 16);
    }

    public CubitObject(String name, BukkitWorld world, Vector minPoint, Vector maxPoint, Vector originPoint) {
        this.name = name;
        this.world = world;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.originPoint = originPoint;
    }
}
