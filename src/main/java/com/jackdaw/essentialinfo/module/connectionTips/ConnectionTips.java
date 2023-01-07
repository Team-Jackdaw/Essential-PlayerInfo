package com.jackdaw.essentialinfo.module.connectionTips;

import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.auxiliary.serializer.Deserializer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
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
                if (serverChangeText.isEmpty()) return;
                sendMessage = this.serverChangeText.replace("%player%", playerName).replace("%previousServer%", previousServer).replace("%server%", server);
            } else {
                sendMessage = "&7" + playerName + ": [" + previousServer + "] -> [" + server + "]";
            }
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(Deserializer.deserialize(sendMessage));
            }
        } else {
            if (isCustomTextEnabled) {
                if (connectionText.isEmpty()) return;
                sendMessage = this.connectionText.replace("%player%", playerName).replace("%server%", server);
            } else {
                sendMessage = "&7" + playerName + ": Connected to [" + server + "].";
            }
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(Deserializer.deserialize(sendMessage));
            }
        }
    }

    //note of disconnect server
    private void disconnectNote(@NotNull DisconnectEvent event) {
        String playerName = event.getPlayer().getUsername();
        String sendMessage;
        if (isCustomTextEnabled) {
            if (disconnectionText.isEmpty()) return;
            sendMessage = this.disconnectionText.replace("%player%", playerName);
        } else {
            sendMessage = "&7" + playerName + ": Exited the servers.";
        }
        for (RegisteredServer s : this.proxyServer.getAllServers()) {
            s.sendMessage(Deserializer.deserialize(sendMessage));
        }
    }
}
