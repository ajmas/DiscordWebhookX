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

import okhttp3.*;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.IOException;

public class Sender
{
    private static void sendMessage(String url, String message) {
        if(!(Webhook.checkUrl(url))) {
            return;
        }

        Response response = null;
        try
        {
            JSONObject obj = new JSONObject().put("content", message);

            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            response = client.newCall(request).execute();
        }
        catch(IOException e)
        {
            Webhook.logError(e);
        }
        finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public static void playerJoin(Player player, Server server, String url)
    {
        Sender.sendMessage(url, "**"+player.getName()+"** Joined the server! Online count: **"+server.getOnlinePlayers().size()+"/"+server.getMaxPlayers()+"**");
    }


    public static void playerLeave(Player player, Server server, String url)
    {
        long count = server.getOnlinePlayers().size()-1;
        Sender.sendMessage(url, "**"+player.getName()+"** Left the server! Online count: **"+count+"/"+server.getMaxPlayers()+"**");
    }

    public static void externalIP(String ipAddress, String url)
    {
        Sender.sendMessage(url, "Server External IP address: **"+ipAddress+"**");
    }

    public static void startup(Server server, String url)
    {
        Sender.sendMessage(url,  "The server is online! Running " + server.getVersion() + ". Max players: **"+server.getMaxPlayers()+"**");
    }

    public static void shutdown(Server server, String url)
    {
        Sender.sendMessage(url,  "The server is going offline! Online players: **"+server.getOnlinePlayers().size()+"**");
    }
}
