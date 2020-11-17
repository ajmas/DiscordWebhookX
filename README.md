# DiscordWebhook

This is a fork of [Artuto's DiscordWebhook](https://www.spigotmc.org/resources/discord-webhook.51537/),
taking things in new directions.

The plugin provides the functionality of reacting to a certain range
of server events and then calling a Discord webhook. While other
webhooks could be called, the payload is adapted is what Discord is
expecting.

## How to Install

Assuming you are installing from a build:

You will first need to have a Bukkit compatible Minecraft server
running, such as [Spigot](https://www.spigotmc.org/) or
[PaperMC](https://papermc.io/). Then copy the following JAR to the plugin
folder of your server:

DiscordWebhook-Spigot-2.1.0-jar-with-dependencies.jar

## How to Configure

On first launch the server will complain that the "The Webhook URL is empty!".
At this point modify the new `DiscordWebhook/config.yml` that was created
in the plugin folder.

Set the URL to the one given to you by Discord and then restart the server.
At this point you should see your first message in the Discord channel you
created the webhook for.

A sample configuration looks as follows:

```yaml
ipCheckUrl: https://api.ipify.org
webhookUrl: https://canary.discordapp.com/api/webhooks
externalPort: 25565
serverName: My Server Name
enabledEvents:
 - playerJoin
 - playerQuit
 - externalIP
 - pluginDisable
 - pluginEnable
```

The settings are as follows:

 - **ipCheckUrl**: The URL of the server to call to get external IP. The
   server should return a plain text response. Pay attention as to whether
   the contents return are an IPv4 or IPv6 address, based on your usage
   context.
 - **webhookUrl**: The URL of the webhook that was provided by your Discord
   channel settings.
 - **externalPort**: The external port to announce. If the port number is
   25565, then it will not be announced, as this is the default port
   Minecraft clients are looking for.
 - **serverName**: The name to use for your Minecraft server. If the value is
   empty, or not provided, then it will be ignored.
 - **enabledEvents**: Which of the supported events the plugin should act
   on and send to Discord.
   - **playerJoin**: The name of the player joining and slot usage
   - **playerQuit**: The name of the player quiting and slot usage
   - **externalIP**: The external IP of the server
   - **pluginDisable**: Announcing the server is going offline
   - **pluginEnable**: Announcing the server has come online

## How to Build

If you are looking to make changes, then you'll need to get both the JDK
(Java Development Kit) and Maven installed on your computer.

These may be available via your system's package manager (apt, chocolatey, brew, MacPorts) or directly from the source sites:

  - https://www.oracle.com/java/technologies/javase-downloads.html
  - https://maven.apache.org/

Once you have them set up you'll need to run Maven:

```
mvn package
```

Results will be in the `target` folder, with base name
`DiscordWebhook-Spigot`. Assuming the current marked release is 2.0.1,
you'll have two files:

  - `DiscordWebhook-Spigot-2.0.1-jar-with-dependencies.jar`
  - `DiscordWebhook-Spigot-2.0.1.jar`

  You should use the first of file in your Minecraft server's plugin
  folder.