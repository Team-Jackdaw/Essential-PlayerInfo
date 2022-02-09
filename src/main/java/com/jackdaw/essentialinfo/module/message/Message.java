package com.jackdaw.essentialinfo.module.message;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Objects;

public class Message {
    // class for Server
    private final ProxyServer proxyServer;
    private final Logger logger;
    private final Parser parser = MessageParser.getParser();

    // connect the module to the plugin and server
    @Inject
    public Message(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    // listener of player chat
    @Subscribe(order = PostOrder.EARLY)
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        HashMap parsedMessage = parser.parse(message);
        if (parsedMessage.get("broadcastTag").equals(true)){
            broadcast(player, parsedMessage.get("content").toString());
        }
    }

    // broadcast the message
    private void broadcast(Player player, String message) {
        String sendMessage;
        // Audience message
        if (player.getCurrentServer().isPresent()){
            sendMessage = "[" + player.getCurrentServer().get().getServerInfo().getName() + "] <" + player.getUsername() + "> " + message;
        } else {
            sendMessage = "<" + player.getUsername() + "> " + message;
            this.logger.warn("Could not find " + player.getUsername() + "'s current server.");
        }
        TextComponent textComponent = Component.text(sendMessage);
        // send message to other server
        for (RegisteredServer s : this.proxyServer.getAllServers()) {
            if (!Objects.equals(s, player.getCurrentServer().get().getServer())) {
                s.sendMessage(textComponent);
            }
        }
    }
}



