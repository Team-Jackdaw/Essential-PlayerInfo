package com.jackdaw.essentialinfo.module.rememberMe;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;

public class RememberMe {
    // class server
    private final ProxyServer proxyServer;
    private final Logger logger;


    // connect the module to the plugin and server
    public RememberMe(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    // listener of player connection
    @Subscribe
    public void connect(ServerConnectedEvent event) {
        connectToPreServer(event);
    }

    // listener of player disconnection
    @Subscribe
    public void disconnect(DisconnectEvent event) {
        remember(event);
    }

    // remember the last server the player exit
    private void remember(@NotNull DisconnectEvent event){
    }

    // connect to the previous server
    private void connectToPreServer(ServerConnectedEvent event){
    }

    // read the server of the player
    private void readFile(Player player){
    }

    // write down the last server when a player exit the proxy
    private void writeFile(Player player, ProxyServer proxyServer){
    }
}
