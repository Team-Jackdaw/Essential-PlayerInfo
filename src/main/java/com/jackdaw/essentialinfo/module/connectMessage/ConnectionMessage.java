package com.jackdaw.essentialinfo.module.connectMessage;

import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.auxiliary.serializer.Deserializer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This module will use miniMessage module to deserializer the connection message.
 */
public class ConnectionMessage {
    private final ProxyServer proxyServer;
    private final boolean isCustomTextEnabled;
    private final String connectionMessageText;
    private final String serverName;

    public ConnectionMessage(ProxyServer proxyServer, @NotNull SettingManager setting) {
        this.proxyServer = proxyServer;
        this.isCustomTextEnabled = setting.isCustomTextEnabled();
        this.connectionMessageText = setting.getConnectionMessageText();
        this.serverName = setting.getServerName();
    }

    @Subscribe
    public void onConnect(ServerConnectedEvent event) {
        connectMessage(event);
    }

    private void connectMessage(@NotNull ServerConnectedEvent event) {
        // send the connection message to the player who just connects to the register server.
        Player player = event.getPlayer();
        RegisteredServer currentServer = event.getServer();
        String sendMessage;
        List<String> serverList = proxyServer
                .getAllServers()
                .stream()
                .map(s -> s.getServerInfo().getName())
                .collect(Collectors.toList());
        if (isCustomTextEnabled) {
            if (connectionMessageText.isEmpty()) return;
            sendMessage = connectionMessageText
                    .replace("%serverName%", serverName)
                    .replace("%player%", player.getUsername())
                    .replace("%server%", currentServer.getServerInfo().getName())
                    .replace("%serverList%", serverListMap(serverList, currentServer.getServerInfo().getName()));
        } else {
            // default connection message
            sendMessage = String.join(""
                    , "<yellow>-------------------------------\nWelcome to "
                    , serverName
                    , "\n-------------------------------\nYou can click the servers below to change your connecting server.\n"
                    , serverListMap(serverList, currentServer.getServerInfo().getName())
                    , "\n-------------------------------\nYou can use <u><light_purple><click:run_command:'/remember'><hover:show_text:'Click to run command'>/remember</hover></click></light_purple></u> to set your default connecting server.\n-------------------------------</yellow>"
            );
        }
        player.sendMessage(Deserializer.miniMessage(sendMessage));
    }

    private @NotNull String serverListMap(@NotNull List<String> serverList, String currentServer) {
        // mapping the serverList to a single String and high line the currentServer.
        String str = "";
        for (String serverName : serverList) {
            if (serverName.equals(currentServer)) {
                // <red><u>[Mirror]</u></red>
                str = String.join("", str, "<red><u>[", serverName, "]</u></red>");
            } else {
                // <green><hover:show_text:'Click to connect to [Survival]'><click:run_command:'/server Survival'>[Survival]</click></hover></green>
                str = String.join("", str, "<green><hover:show_text:'Click to connect to [", serverName, "]'><click:run_command:'/server ", serverName, "'>[", serverName, "]</click></hover></green>");
            }
        }
        return str;
    }
}
