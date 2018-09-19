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

import java.io.File;
import java.io.IOException;

public class PasteData {
    private File cubitFile;
    private BukkitWorld world;
    private Vector position;
    private boolean allowUndo;
    private boolean noAir;

    private boolean paste() {
        try {
            ClipboardFormat format = ClipboardFormat.findByFile(cubitFile);
            Schematic schematic = format.load(cubitFile);
            schematic.paste(world, position, allowUndo, !noAir, null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
