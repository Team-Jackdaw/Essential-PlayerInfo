# Essential-PlayerInfo

## Introduce

This is a very lightweight plugin for Velocity proxy.

Used API of Velocity 3.1.1, so it may only support Velocity version 3.1.1 or above, and other versions have not been tested.

## Core Features

**Global PingList**

Display the player ID on the Minecraft server list.

![PingList.png][1]

**Global TabList**

Show global players on the TabList.

![TabList1.png][2]

**Global Chat & ConnectionTips**

The most basic cross-server chat function. & Simple connection tips.

![customText1.png][3]

**Remember my initial connecting server**

You can now let your players set the initial connecting server as they like. The default setting is "remember their last connected server".

Players can use command to set the mode and server.

- Set default mode
  - Description: If set to `true`, players will connect to a specific server every time when they connect to the proxy. This initial server can be set by another command.
    If set to `false`, players will connect to the server they last exit.
  - Usage: `/remember mode <true, false>`
  - Default: `false`


- Set initial server
    - Description: Only work if the default mode is `true`. Set the initial connecting server.
    - Usage: `/remember server <servername>`
    - Default: `null` (If null, players will connect to the default server of the proxy)
    
## Optional Features

**Customize your message text**

You can change the custom text in the config file. You can use &-codes to color the message.

You can use `%player%` `%server%` and `%previousServer%` to replace the variable.

**Command-to-broadcast**

For those who don't want their servers to be continuous global broadcasts, we provide an option to enable "command to
broadcast". Which means the chat messages in these servers would be broadcast only when the beginning of the message
is "#".

## Config

**Notes**

 If you update from an old version, your config file will be recreated, and your setting will be reset to default.

    # essential-playerinfo
    # Configuration version. !Please do not change this option!
    [version]
        version="v3.0"
    
    # Global tablist
    [tabList]
        enabled=true
    
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
        enable=true
    
    # Custom Message Setting
    [customText]
      enable=false
      connectionText = "&7%player%: Connect to [%server%]."
      serverChangeText = "&7%player%: [%previousServer%] -> [%server%]"
      isconnectionText = "&7%player%: Exit the servers."
      chatText = "&7[%server%] <%player%> "

## To do list

- [ ] Get the server list and provide a way to click to switch.

- [ ] Add an advance way to customize the messages including some extra feature (By `miniMessage` format).

## Notes

Message authentication has been updated to the new version (1.19 or higher) of Minecraft. To avoid surprises with non-online authentication servers, you can set `enable-secure-profile` to `false` in the `server.properties` of each server.

## Build

Clone the repository

Open a command prompt/terminal to the repository directory

run 'gradlew build'

The built jar file will be in build/libs/

[1]: https://cdn.ussjackdaw.com/image/PingList.png

[2]: https://cdn.ussjackdaw.com/image/TabList1.png

[3]: https://cdn.ussjackdaw.com/image/customText1.png
