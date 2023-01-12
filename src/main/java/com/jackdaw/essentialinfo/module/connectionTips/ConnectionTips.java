package com.jackdaw.essentialinfo.module.connectionTips;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.auxiliary.serializer.Deserializer;
import com.jackdaw.essentialinfo.module.AbstractComponent;
import com.jackdaw.essentialinfo.module.VelocityDataDir;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.nio.file.Path;

public class ConnectionTips extends AbstractComponent {
    // class server
    private final String connectionText;
    private final String serverChangeText;
    private final String disconnectionText;
    private final boolean isCustomTextEnabled;

    @Inject
    public ConnectionTips(ProxyServer proxyServer, Logger logger, @VelocityDataDir Path velocityDataDir, SettingManager setting) {
        super(proxyServer, logger, velocityDataDir, setting);
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
                // "<gray>%player%: <u><click:run_command:'/server %previousServer%'><hover:show_text:'Click to switch.'>[%previousServer%]</hover></click></u> -> <u><click:run_command:'/server %server%'><hover:show_text:'Click to switch.'>[%server%]</hover></click></u></gray>"
                sendMessage = String.join(""
                        , "<gray>"
                        , playerName
                        , ": <u><click:run_command:'/server "
                        , previousServer
                        , "'><hover:show_text:'Click to switch.'>["
                        , previousServer
                        , "]</hover></click></u> -> <u><click:run_command:'/server "
                        , server
                        , "'><hover:show_text:'Click to switch.'>["
                        , server
                        , "]</hover></click></u></gray>"
                );
            }
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(Deserializer.miniMessage(sendMessage));
            }
        } else {
            if (isCustomTextEnabled) {
                if (connectionText.isEmpty()) return;
                sendMessage = this.connectionText.replace("%player%", playerName).replace("%server%", server);
            } else {
                // "<gray>%player%: Connect to <u><hover:show_text:'Click to switch.'><click:run_command:'/server %server%'>[%server%]</click></hover></u>.</gray>"
                sendMessage = String.join(""
                        , "<gray>"
                        , playerName
                        , ": Connect to <u><hover:show_text:'Click to switch.'><click:run_command:'/server "
                        , server
                        , "'>["
                        , server
                        , "]</click></hover></u>.</gray>"
                );
            }
            for (RegisteredServer s : this.proxyServer.getAllServers()) {
                s.sendMessage(Deserializer.miniMessage(sendMessage));
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
            // "<gray>%player%: Exit the servers.</gray>"
            sendMessage = String.join(""
                    , "<gray>"
                    , playerName
                    , ": Exit the servers.</gray>"
            );
        }
        for (RegisteredServer s : this.proxyServer.getAllServers()) {
            s.sendMessage(Deserializer.miniMessage(sendMessage));
        }
    }
}
