# Essential-PlayerInfo

## Introduce

This is a very lightweight plugin for Velocity proxy.

Used API of Velocity 3.1.0, so it may only support Velocity version 3.1.0 and above, and other versions have not been tested.

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

## Optional Features

**Customize your message text**

You can change the custom text in the config file. You can use &-codes to color the message.

You can use %player% %server% and %previousServer% to replace the variable.

**Command-to-broadcast**

For those who don't want their servers to be continuous global broadcasts, we provide an option to enable "command to
broadcast". Which means the chat messages in these servers would be broadcast only when the beginning of the message
is "#".

## Config

    # essential-playerinfo
    # Configuration version. !Please do not change this option!
    [version]
        version="v2.0"
    
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
    # e.g. WDRshadow: Connect to [Server1].
    connectionText = "&7%player%: Connect to [%server%]."
    # e.g. WDRshadow: [Server1] -> [Server2]
    serverChangeText = "&7%player%: [%previousServer%] -> [%server%]"
    # e.g. WDRshadow: Exit the servers.
    disconnectionText = "&7%player%: Exit the servers."
    # e.g. [Server1] <WDRshadow> Hello World!
    chatText = "&7[%server%] <%player%> "

## To do list

**1. Get the server list and provide a way to click to switch.**

**2. Let players customize the default server.**

**3. Add an advance way to customize the messages including some extra feature (By miniMessage format).**

## Build

Clone the repository

Open a command prompt/terminal to the repository directory

run 'gradlew build'

The built jar file will be in build/libs/

[1]: https://cdn.ussjackdaw.com/image/PingList.png

[2]: https://cdn.ussjackdaw.com/image/TabList1.png

[3]: https://cdn.ussjackdaw.com/image/customText1.png
