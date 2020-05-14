# DiscordWebhook

This is a fork of [Artuto's DiscordWebhook](https://www.spigotmc.org/resources/discord-webhook.51537/),
taking things in new directions.

The plugin provides the functionality of reacting to a certain range
of server events and then calling a Discord webhook. While other
webhooks could be called, the payload is adapted is what Discord is
expecting.

## How to Install

Assuming you are installing from a build:

You will first need to have a [Spigot](https://www.spigotmc.org/)
Minecraft server running. Then copy the following JAR to the Spigot
build folder:

DiscordWebhook-Spigot-1.0.1-jar-with-dependencies.jar

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