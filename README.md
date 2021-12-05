# Essential-PlayerInfo

## Introduce

This is a very lightweight plugin for Velocity proxy.

Used API of Velocity 3.1.0, so it may only support Velocity version 3.1.0 and above, and other versions have not been tested.

## Feature

**Global pingList**

Display the player ID on the Minecraft server list.

![PingList.png][1]

**Global tabList**

Show global players on the TabList.

![TabList.png][2]

**Global chat**

The most basic cross-server chat function.

![Message.png][3]

## Config

    # essential-playerinfo
    # Global tablist
    [tabList]
        enabled=true

    # Global massage
    [message]
        enabled=true

    # Ping List
    [pingList]
        enabled=true

## To do list

**1. Get the server list and provide a way to click to switch.**

**2. Let players customize the default server.**

## Build

Clone the repository

Open a command prompt/terminal to the repository directory

run 'gradlew build'

The built jar file will be in build/libs/

[1]: https://cdn.ussjackdaw.com/image/PingList.png
[2]: https://cdn.ussjackdaw.com/image/TabList.png
[3]: https://cdn.ussjackdaw.com/image/Message.png