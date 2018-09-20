/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitWorldTools;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;

public class CubitObject {
    public String name;
    public BukkitWorld world;
    public Vector minPoint;
    public Vector maxPoint;
    public Vector zeroPoint;

    public CubitObject(String name, BukkitWorld world, Vector minPoint, Vector maxPoint, Vector zeroPoint) {
        this.name = name;
        this.world = world;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.zeroPoint = zeroPoint;
    }
}
