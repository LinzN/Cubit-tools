/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitTools;


import de.linzn.cubitTools.plugin.CubitToolsPlugin;
import de.linzn.cubitTools.regenerator.CubitWorldRegenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CToolsCommand implements CommandExecutor {
    private CubitToolsPlugin plugin;
    private CubitWorldRegenerator regenerator;

    public CToolsCommand(CubitToolsPlugin plugin, CubitWorldRegenerator regenerator) {
        this.plugin = plugin;
        this.regenerator = regenerator;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "cleanup":
                    String[] modifiedArgs = Arrays.copyOfRange(args, 1, args.length);
                    cleanCommand(commandSender, modifiedArgs);
                    break;
                default:
                    helpCommand(commandSender);
                    break;
            }
        } else {
            helpCommand(commandSender);
        }
        return true;
    }

    private void cleanCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player) {
            commandSender.sendMessage(ChatColor.DARK_RED + "Sorry but THIS is only available from the server console!");
            return;
        }
        if (args.length > 0) {
            switch (args[0]) {
                case "start":
                    if (args.length <= 1) {
                        return;
                    }
                    World world = Bukkit.getWorld(args[1]);
                    if (world == null) {
                        commandSender.sendMessage(ChatColor.RED + "The world: " + args[1] + " is not loaded or doesn't exist");
                        return;
                    }
                    commandSender.sendMessage(ChatColor.GOLD + "Try to start world cleanup for: " + world.getName());
                    this.plugin.getServer().getScheduler().runTask(this.plugin, () -> this.regenerator.startCleanupWorld(world));
                    break;
                case "cancel":
                    commandSender.sendMessage(ChatColor.GOLD + "Try to cancel exist world cleanup");
                    this.regenerator.setRunning(false);
                    break;
                default:
                    helpCommand(commandSender);
                    break;
            }
        } else {
            helpCommand(commandSender);
        }
    }

    private void helpCommand(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.DARK_PURPLE + "###################################################");
        commandSender.sendMessage(ChatColor.GOLD + "Start new world cleanup: "+ChatColor.YELLOW+"/ctools cleanup start <world>");
        commandSender.sendMessage(ChatColor.GOLD + "Cancel exist world cleanup: "+ChatColor.YELLOW+"/ctools cleanup cancel");
        commandSender.sendMessage(ChatColor.DARK_PURPLE + "###################################################");
    }
}
