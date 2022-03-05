package com.jackdaw.essentialinfo.module.connectionTips;

import com.jackdaw.essentialinfo.configuration.SettingManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
        String str;
        Component sendMessage;
        if (event.getPreviousServer().isPresent()) {
            String previousServer = event.getPreviousServer().get().getServerInfo().getName();
            if (isCustomTextEnabled) {
                str = this.serverChangeText.replace("%player%", playerName).replace("%previousServer%", previousServer).replace("%server%", server);
            } else {
                str = "&7" + playerName + ": [" + previousServer + "] -> [" + server + "]";
            }
            // sendMessage = LegacyComponentSerializer.legacyAmpersand().deserialize(str);
            sendMessage = MiniMessage.miniMessage().deserialize(str);
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(sendMessage);
            }
        } else {
            if (isCustomTextEnabled) {
                str = this.connectionText.replace("%player%", playerName).replace("%server%", server);
            } else {
                str = "&7" + playerName + ": Connected to [" + server + "].";
            }
            // sendMessage = LegacyComponentSerializer.legacyAmpersand().deserialize(str);
            sendMessage = MiniMessage.miniMessage().deserialize(str);
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(sendMessage);
            }
        }
    }

    //note of disconnect server
    private void disconnectNote(@NotNull DisconnectEvent event) {
        String playerName = event.getPlayer().getUsername();
        String str;
        Component sendMessage;
        if (isCustomTextEnabled) {
            str = this.disconnectionText.replace("%player%", playerName);
        } else {
            str = "&7" + playerName + ": Exited the servers.";
        }
        // sendMessage = LegacyComponentSerializer.legacyAmpersand().deserialize(str);
        sendMessage = MiniMessage.miniMessage().deserialize(str);
        for (RegisteredServer s : this.proxyServer.getAllServers()) {
            s.sendMessage(sendMessage);
        }
    }
}
