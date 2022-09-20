/*
 * Copyright (C) 2017 Artu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.artuto.discordwebhook;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.artuto.discordwebhook.loader.Config;
import static me.artuto.discordwebhook.Const.*;

public class CommandHandler implements CommandExecutor {
    Config config;

    CommandHandler(Config config) {
        super();
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender executor, Command cmd, String label, String[] args) {
        if (cmd.getName().toLowerCase().equals(COMMAND_NAME)) {
            if(args.length == 0 || args[0].equals(SUBCOMMAND_HELP)) {
                StringBuilder output = new StringBuilder();

                output.append(
                    ChatColor.DARK_BLUE +
                    "-====== Discord Webhook Help ======-" + ChatColor.RESET  +
                    "\n/" + COMMAND_NAME + " about - Shows information about the plugin"  +
                    "\n/" + COMMAND_NAME + " help - Shows this message"
                );

                if (config.isCommandEnabled(SUBCOMMAND_MSG)) {
                    output.append(
                        "\n/" + COMMAND_NAME + " msg - Sends a message to the Discord channel"
                    );
                }

                if (config.isCommandEnabled(SUBCOMMAND_LOCATION_MSG)) {
                    output.append(
                        "\n/" + COMMAND_NAME + " msg - Shares your location with the Discord channel"
                    );
                }

                executor.sendMessage(output.toString());
            }
            else if(args[0].equals(SUBCOMMAND_ABOUT)) {
                executor.sendMessage("Plugin made by " + ChatColor.GREEN +
                    "Artuto and continued by ajmas " +
                    ChatColor.RESET + "\nPlugin Version: " +
                    ChatColor.GREEN + VERSION + ChatColor.RESET);
            }
            else if(args[0].equals(SUBCOMMAND_MSG) && config.isCommandEnabled(SUBCOMMAND_MSG) && args.length > 1) {
                String message =  String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                Sender.sendPlayerMessage(executor.getServer(), executor.getName(), message, config.getUrl());
            }
            else if(args[0].equals(SUBCOMMAND_LOCATION_MSG) && config.isCommandEnabled(SUBCOMMAND_LOCATION_MSG)) {
                Player player = executor.getServer().getPlayer(executor.getName());
                if (player != null) {
                    Location location = player.getLocation();

                    String message = String.format(
                        "My location is %d, %d, %d (%s@%s)",
                        location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ(),
                        location.getWorld().getName(),
                        config.getServerName()
                    );
                    Sender.sendPlayerMessage(executor.getServer(), executor.getName(), message, config.getUrl());
                } else {
                    executor.sendMessage(executor.getName() + " does not have a location");
                }
            }
            else {
                executor.sendMessage("Don't know what to do with: " + args[0]);
            }
            return true;
        }

        return false;
    }
}
