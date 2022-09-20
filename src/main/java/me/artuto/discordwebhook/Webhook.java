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

import me.artuto.discordwebhook.loader.Config;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.Reader;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("WeakerAccess")
public class Webhook extends JavaPlugin implements Runnable
{
    public static final int DEFAULT_PORT = Const.DEFAULT_PORT;

    public static Webhook plugin;
    private String serverName;
    private int serverPort = DEFAULT_PORT;
    private String externalIP;
    private String ipCheckUrl;
    private String discordUrl;
    private boolean enabled;
    private Thread taskThread;
    private long taskInterval = 900000;

    @Override
    public void onEnable()
    {
        plugin = this;
        this.enabled = true;

        plugin.getServer().getLogger().info(String.format("Enabling DiscordWebhook V.%s...", Const.VERSION));
        Config config = new Config(this.getConfig());
        saveConfig();
        CommandHandler cmdHandler = new CommandHandler(config);

        if(config.getUrl().isEmpty())
        {
            getServer().getLogger().warning("The webhook URL is not configured!");
        }

        this.getCommand(Const.COMMAND_NAME).setExecutor(cmdHandler);
        getServer().getPluginManager().registerEvents(new EventListener(config), this);

        this.discordUrl = config.getUrl();
        this.serverName = config.getServerName();
        this.serverPort = config.getExternalPort();

        // Checks the server's external IP and announces it
        if (config.getEnabledEvents().indexOf("externalIP") > -1) {
            this.ipCheckUrl = config.getIPCheckUrl();
            this.taskThread = new Thread(this, "DiscordWebookTasks");
            this.taskThread.start();
        }
    }

    @Override
    public void onDisable()
    {
        this.enabled = false;

        // interupt the thread if it is running
        if (this.taskThread != null) {
            this.taskThread.interrupt();
        }

        plugin.getLogger().info(String.format("Disabling DiscordWebhook V.%s...", Const.VERSION));
        getServer().getScheduler().cancelTasks(this);


    }

    public String getExternalIP(String ipCheckUrl)
    {
        String address = null;
        Response response = null;
        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ipCheckUrl)
                    .get()
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful())
            {
                StringBuilder strBuilder = new StringBuilder();
                char[] buffer = new char[128];
                Reader reader = response.body().charStream();
                int len = -1;
                while ((len = reader.read(buffer)) > -1) {
                    strBuilder.append(buffer, 0, len);
                }
                address = strBuilder.toString();
            }
        }
        catch (Exception ex)
        {
            logError(ex);
        }
        finally
        {
            if (response != null)
            {
                response.close();
            }
        }
        return address;
    }


    public static void logError(Exception e)
    {
        plugin.getServer().getLogger().severe("Error with DiscordWebhook "+e);
        e.printStackTrace();
    }


    public static boolean checkUrl(String url)
    {
        if(url.trim().isEmpty() || url.trim().equals("https://canary.discordapp.com/api/webhooks"))
        {
            plugin.getLogger().severe("The Webhook URL is empty!");
            return false;
        }

        return true;
    }

    @Override
    public void run()
    {
        while (this.enabled) {
            try
            {
                String newExternalIP = this.getExternalIP(this.ipCheckUrl);
                if (!newExternalIP.equals(this.externalIP)) {
                    this.externalIP = newExternalIP;
                    plugin.getLogger().info("External IP: " + this.externalIP);

                    String address = this.externalIP;
                    if (this.serverPort != Webhook.DEFAULT_PORT) {
                        address += ":" + this.serverPort;
                    }

                    String serverVersion = this.getServer().getVersion();
                    Sender.externalAddress(address, this.serverName, serverVersion, this.discordUrl);
                }

                // sleep for 15 minutes
                Thread.sleep(this.taskInterval);
            }
            catch (InterruptedException ex)
            {
                // ignored
            }
        }
        plugin.getLogger().info("Stopping periodic Discord Webhook tasks");
    }
}
