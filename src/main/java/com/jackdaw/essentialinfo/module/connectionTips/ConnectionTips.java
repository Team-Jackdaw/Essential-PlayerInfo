package com.jackdaw.essentialinfo.module.connectionTips;

import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

public class ConnectionTips {
    // class server
    private final ProxyServer proxyServer;
    private final String connectionText;
    private final String serverChangeText;
    private final String disconnectionText;
    private final boolean isCustomTextEnabled;

    // connect the module to the plugin and server
    public ConnectionTips(ProxyServer proxyServer, SettingManager setting) {
        this.proxyServer = proxyServer;
        this.isCustomTextEnabled = setting.isCustomTextEnabled();
        this.connectionText = setting.getConnectionText();
        this.serverChangeText = setting.getServerChangeText();
        this.disconnectionText = setting.getDisconnectionText();
    }

    // listener of player login
    @Subscribe
    public void connect(ServerConnectedEvent event) {
        connectNote(event);
    }

    // listener of player disconnect
    @Subscribe
    public void disconnect(DisconnectEvent event) {
        disconnectNote(event);
    }

    // note of connect server
    private void connectNote(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getUsername();
        String server = event.getServer().getServerInfo().getName();
        String sendMessage;
        if (event.getPreviousServer().isPresent()) {
            String previousServer = event.getPreviousServer().get().getServerInfo().getName();
            if (isCustomTextEnabled) {
                sendMessage = this.serverChangeText.replace("%player%", playerName).replace("%previousServer%", previousServer).replace("%server%", server);
            } else {
                sendMessage = playerName + ": [" + previousServer + "] -> [" + server + "]";
            }
            TextComponent textComponent = Component.text(sendMessage);
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(textComponent);
            }
        } else {
            if (isCustomTextEnabled) {
                sendMessage = this.connectionText.replace("%player%", playerName).replace("%server%", server);
            } else {
                sendMessage = playerName + ": Connected to [" + server + "].";
            }
            TextComponent textComponent = Component.text(sendMessage);
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(textComponent);
            }
        }
    }

    //note of disconnect server
    private void disconnectNote(@NotNull DisconnectEvent event) {
        String playerName = event.getPlayer().getUsername();
        String sendMessage;
        if (isCustomTextEnabled) {
            sendMessage = this.disconnectionText.replace("%player%", playerName);
        } else {
            sendMessage = playerName + ": Exited the servers.";
        }
        TextComponent textComponent = Component.text(sendMessage);
        for (RegisteredServer s : this.proxyServer.getAllServers()) {
            s.sendMessage(textComponent);
        }
    }
}
