/*
 * Copyright (C) 2018 Artu
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
package me.artuto.discordwebhook.loader;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.artuto.discordwebhook.Const;

public class Config {
    private final FileConfiguration config;

    public Config(FileConfiguration config) {
        this.config = config;
        config.addDefault("webhookUrl", "https://canary.discordapp.com/api/webhooks");
        config.addDefault("ipCheckUrl", "https://api.ipify.org");
        config.addDefault("serverName", "");
        config.addDefault("externalPort", Const.DEFAULT_PORT);
        config.addDefault("enabledCommmands", new String[] { Const.SUBCOMMAND_MSG });
        config.addDefault("enabledEvents",
            Arrays.asList(new String[] { "playerJoin", "playerQuit", "externalIP",
            "pluginDisable", "pluginEnable" }));
        config.options().copyDefaults(true);
    }

    public String getUrl() {
        return config.getString("webhookUrl");
    }

    public String getIPCheckUrl() {
        return config.getString("ipCheckUrl");
    }

    public String getServerName() {
        String name = config.getString("serverName");
        if (name == null || name.trim().length() == 0 ) {
            name = null;
        }
        return name;
    }

    public int getExternalPort() {
        return config.getInt("externalPort");
    }

    public List<String> getEnabledEvents() {
        return config.getStringList("enabledEvents");
    }

    public boolean isEventEnabled(String command) {
        return getEnabledEvents().indexOf(command) > -1;
    }

    public List<String> getEnabledCommands() {
        return config.getStringList("enabledCommands");
    }

    public boolean isCommandEnabled(String command) {
        return getEnabledCommands().indexOf(command) > -1;
    }
}