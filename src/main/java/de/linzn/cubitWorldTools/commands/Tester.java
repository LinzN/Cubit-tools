/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitWorldTools.commands;

import de.linzn.cubit.api.CubitAPI;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import de.linzn.cubitWorldTools.api.CubitSnipper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tester implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        CubitLand cLand = CubitAPI.getCubitLand(p.getLocation().getChunk());
        if (cLand == null) {
            p.sendMessage("Not a cubit land!");
            return true;
        }
        CubitSnipper.saveCubitRegion(cLand.getLandName(), p.getLocation().getChunk(), true);
        return true;
    }
}
