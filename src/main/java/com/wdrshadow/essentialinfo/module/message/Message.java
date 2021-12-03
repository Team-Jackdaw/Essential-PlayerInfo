package com.wdrshadow.essentialinfo.module.message;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.wdrshadow.essentialinfo.EssentialInfo;

import java.util.Collection;

public class Message {
    // class for Server
    private final ProxyServer proxyServer;

    // connect the module to the plugin and server
    public Message(EssentialInfo plugin, ProxyServer proxyServer){
        this.proxyServer = proxyServer;
    }

    // listener of player chat
    @Subscribe(order = PostOrder.EARLY)
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        broadcast(player, message);
    }

    // broadcast the message
    public void broadcast(Player player, String message){
        // get players on the proxy exclude those who send message
        Collection<Player> players = proxyServer.getAllPlayers();
        players.remove(player);
        // send message to other players
        for(Player p: players){
            p.spoofChatInput("["+player.getUsername()+"]:"+ message);
        }

    }
}

