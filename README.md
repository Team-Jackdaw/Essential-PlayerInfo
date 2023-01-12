# Essential-PlayerInfo

## Introduce

This is a very lightweight plugin for Velocity proxy.

Used API of Velocity 3.1.2, so it may only support Velocity version 3.1.2 or above, and other versions have not been
tested.

**Please aware that this branch is a snapshot, we have used some new APIs under Velocity 3.1.2-SNAPSHOT. This Plugin
probably not work in lower version.**

## Core Features

### Global PingList

Display the player ID on the Minecraft server list.

![PingList.png][1]

### Global TabList

Show global players on the TabList.

- You can now customize the display mode and display message of global players. (see config)

![TabList2.png][2]

### Connection Message

Welcome message will be sent to every player when they connect to a register server. And provide a way to click to
switch servers.

- You can now customize the message in config file.

![ConnectMessage.png][3]

### Global Chat & ConnectionTips

The most basic cross-server chat function. & Simple connection tips.

- You can now click the _[ServerName]_ to switch to the target server.

![customText2.png][4]

### Remember my initial connecting server

A feature to let your players set the initial connecting server as they like. The default setting is "remember their
last connected server".

Players can use command to set the mode and server.

- Set default mode
  - Description: If set to `preset`, players will connect to a specific server every time when they connect to the
    proxy. This initial server can be set by another command. If set to `last`, players will connect to the server they
    last exit.
  - Usage: `/remember mode <last, preset>`
  - Default: `last`


- Set initial server
  - Description: Only work if the default mode is `preset`. Set the initial connecting server.
  - Usage: `/remember server <servername>`
  - Default: `null` (If null, players will connect to the default server of the proxy)

Players can also use click to command with `/remember` or connection message.

![RememberMe.png][5]

## Optional Features

### Customize your message text

You can change the custom text in the config file. You can use MiniMessage to color the message.

You can use `%player%`, `%server%` ,`%previousServer%` and `%serverList%` to replace the variable.

### Command-to-broadcast

For those who don't want their servers to be continuous global broadcasts, we provide an option to enable "command to
broadcast". Which means the chat messages in these servers would be broadcast only when the beginning of the message
is "#".

## Config

**Notes**

- **Please aware that the custom text has been updated to `MiniMessage` format in version `v3.2.0` or higher. You should
  use `MiniMessage` format to customize the message. You can use [MiniMessage Viewer](https://webui.adventure.kyori.net/)
  to preview your text.**

- If you update from an old version, your config file will be recreated, and your setting will be reset to default. We
  recommend that you should back up your old config file before you updating this plugin.

- You can now disable any custom message by setting a component blank with `""`.

The default config file is shown below:

    # essential-playerinfo
    # Configuration version. !Please do not change this option!
    [version]
        version="v3.2"
    
    # Global tablist
    [tabList]
        enabled=true
        # `0` for survival, `1` for creative, `2` for adventure, `3` for spectator.
        displayMode=3
    
    # Global massage
    [message]
        enabled=true
        command-to-broadcast=false
    
    # Ping List
    [pingList]
        enabled=true
    
    # Connection Tips
    [connectionTips]
        enabled=true
    
    # Remember me
    [rememberMe]
        enabled=true
    
    # Connection Message
    [connectMessage]
        enabled=true
        serverName = "Example Server"
    
    # Custom Message Setting
    [customText]
        enabled = false
        # All custom text need to be in the miniMessage format in version v3.1.0 (Config file version v3.2) or higher.
        # You can use MiniMessage Viewer to preview your text. https://webui.adventure.kyori.net/
        connectionText = "<gray>%player%: Connect to <u><hover:show_text:'Click to switch.'><click:run_command:'/server %server%'>[%server%]</click></hover></u>.</gray>"
        serverChangeText = "<gray>%player%: <u><click:run_command:'/server %previousServer%'><hover:show_text:'Click to switch.'>[%previousServer%]</hover></click></u> -> <u><click:run_command:'/server %server%'><hover:show_text:'Click to switch.'>[%server%]</hover></click></u></gray>"
        disconnectionText = "<gray>%player%: Exit the servers.</gray>"
        chatText = "<gray><u><click:run_command:'/server %server%'><hover:show_text:'Click to switch.'>[%server%]</hover></click></u> <%player%> "
        tabListText = "[%server%] %player%"
        connectionMessageText = "<yellow>-------------------------------\nWelcome to %serverName%!\n-------------------------------\nYou can click the servers below to change your connecting server.\n%serverList%\n-------------------------------\nYou can use <u><light_purple><click:run_command:'/remember'><hover:show_text:'Click to run command'>/remember</hover></click></light_purple></u> to set your default connecting server.\n-------------------------------</yellow>"

## To do list

- [x] Get the server list and provide a way to click to switch.

- [x] Add an advance way to customize the messages including some extra feature (By `miniMessage` format).

- [ ] Reconfigure the whole plugin before programming next feature.

- [ ] Update the config file and make it more easy to build the custom text.

- [ ] Adapted to message validation for `Minecraft 1.19` or higher.

- [ ] Record the online period time of players.

- [ ] Show the time (or opening time) of server.

## Build

Clone the repository

Open a command prompt/terminal to the repository directory

run 'gradlew build'

The built jar file will be in build/libs/

[1]: https://cdn.ussjackdaw.com/image/PingList.png

[2]: https://cdn.ussjackdaw.com/image/TabList2.png

[3]: https://cdn.ussjackdaw.com/image/ConnectMessage1.png

[4]: https://cdn.ussjackdaw.com/image/customText2.png

[5]: https://cdn.ussjackdaw.com/image/RememberMe1.png
