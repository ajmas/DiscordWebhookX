# DiscordWebhookX

This is a fork of [Artuto's DiscordWebhook](https://www.spigotmc.org/resources/discord-webhook.51537/),
which has been abandoned since 2019. Here we add new
features, behaviours and tweaks.

The plugin provides the functionality of reacting to a certain range
of server events and then calling a Discord webhook. While other
webhooks could be called, the payload is adapted is what Discord is
expecting.

Contributions and suggestions are appreciated 😃

## Tested against

 - Pre 2.2.0 version: Tested to work with MC 1.15.x, 1.16.x, 1.17.x, 1,18.x
 - 2.2.0 and up: Tested to work with MC 1.19.x

## How to Install

Assuming you are installing from a build:

You will first need to have a Bukkit compatible Minecraft server
running, such as [Spigot](https://www.spigotmc.org/) or
[PaperMC](https://papermc.io/). Then copy the following JAR to the plugin
folder of your server:

DiscordWebhook-Spigot-2.2.0-jar-with-dependencies.jar

## How to use

By default, all events and commands are enabled, though you should still
configure the plugin for your server. See "[How to Configure](#how-to-configure)"
section below for details.

Help for each command is provided in game, or via the server console, via
`/discordmc help`.

## How to Configure

On first launch the server will complain that the "The Webhook URL is empty!".
At this point modify the new `DiscordWebhook/config.yml` that was created
in the `plugins` folder of your server.

Set the URL to the one given to you by Discord (see below if you aren't sure)
and then restart the server. At this point you should see your first message
in the Discord channel you created the webhook for.

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
enabledCommands:
 - msg
 - location-msg
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
 - **enabledCommands**: Which of the sub commands are enabled
   - **msg**: Allows the player to send a message to the Discord channel
   - **location-msg**: Sends the location of the player to the Discord channel,
     as if they wrote it


## Getting the Discord Webhook URL

If you have admin access to your Discord server or guild, the steps are
as follows (assuming the UI hasn't changed):

 - Next to the text channel, click on the gear icon
 - Go to the 'integrations' section
 - Click on 'Create Webhook'
 - Name your webhook and give it a name
 - Click on the 'Copy Webhook URL' and use that URL in the `webhookUrl`
   of your configuration.

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
`DiscordWebhook-Spigot`. Assuming the current marked release is 2.2.0
(see version value in pom.xml), you'll have two files:

  - `DiscordWebhook-Spigot-2.2.0-jar-with-dependencies.jar`
  - `DiscordWebhook-Spigot-2.2.0.jar`

  You should use the first of the files in your Minecraft server's plugin
  folder. The second one will not work, since it is missing the necessary
  dependencies.

  ## License

  This project is licensed under GPLv3. See attached [LICENSE](./LICENSE)
  file for text of the license.