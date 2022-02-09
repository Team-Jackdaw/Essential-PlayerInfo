package com.jackdaw.essentialinfo.module.connectionTips;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ConnectionTips {
    // class server
    private final ProxyServer proxyServer;

    // connect the module to the plugin and server
    public ConnectionTips(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
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
    private void connectNote(ServerConnectedEvent event){
        Player player = event.getPlayer();
        String sendMessage;
        if (event.getPreviousServer().isPresent()){
            sendMessage = player.getUsername() + ": ["
                    + event.getPreviousServer().get().getServerInfo().getName() + "] -> ["
                    + event.getServer().getServerInfo().getName() + "]";
            TextComponent textComponent = Component.text(sendMessage);
            for (RegisteredServer s : this.proxyServer.getAllServers()){
                s.sendMessage(textComponent);
            }
        } else {
            sendMessage = player.getUsername() + ": Connected to ["
                    + event.getServer().getServerInfo().getName() + "].";
            TextComponent textComponent = Component.text(sendMessage);
            for (RegisteredServer s : this.proxyServer.getAllServers()){
                s.sendMessage(textComponent);
            }
        }
    }

    //note of disconnect server
    private void disconnectNote(DisconnectEvent event){
        Player player = event.getPlayer();
        String sendMessage = player.getUsername() + ": Exited the servers.";
        TextComponent textComponent = Component.text(sendMessage);
        for (RegisteredServer s : this.proxyServer.getAllServers()){
            s.sendMessage(textComponent);
        }
    }
}
