package com.wdrshadow.essentialinfo.module.message;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Objects;

public class Message {
    // class for Server
    private final ProxyServer proxyServer;
    private final Logger logger;

    // connect the module to the plugin and server
    @Inject
    public Message(ProxyServer proxyServer, Logger logger){
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    // listener of player chat
    @Subscribe(order = PostOrder.EARLY)
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        broadcast(player, message);
    }

    // broadcast the message
    private void broadcast(Player player, String message){
        // get players on the proxy exclude those who send message
        Collection<Player> players = proxyServer.getAllPlayers();
        // send message to other players
        for(Player p: players){
            if(!Objects.equals(p.getUsername(), player.getUsername())){
                p.spoofChatInput("<"+player.getUsername()+"> "+ message);
            }
        }
    }
}

