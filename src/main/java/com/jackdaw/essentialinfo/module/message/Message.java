package com.jackdaw.essentialinfo.module.message;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.auxiliary.serializer.Deserializer;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Objects;

public class Message {
    // class for Server
    private final ProxyServer proxyServer;
    private final Parser parser = MessageParser.getParser();
    private final boolean isCommandToBroadcast;
    private final boolean isCustomTextEnabled;
    private final String chatText;

    // connect the module to the plugin and server
    @Inject
    public Message(ProxyServer proxyServer, SettingManager setting) {
        this.isCommandToBroadcast = setting.isCommandToBroadcastEnabled();
        this.proxyServer = proxyServer;
        this.isCustomTextEnabled = setting.isCustomTextEnabled();
        this.chatText = setting.getChatText();
    }

    // listener of player chat
    @Subscribe(order = PostOrder.EARLY)
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (this.isCommandToBroadcast) {
            HashMap parsedMessage = parser.parse(message);
            if (parsedMessage.get("broadcastTag").equals(true)) {
                broadcast(player, parsedMessage.get("content").toString());
            }
        } else {
            broadcast(player, message);
        }

    }

    // broadcast the message
    private void broadcast(Player player, String message) {
        String playerName = player.getUsername();
        String sendMessage;
        // Audience message
        if (player.getCurrentServer().isPresent()) {
            String server = player.getCurrentServer().get().getServerInfo().getName();
            if (this.isCustomTextEnabled) {
                if (this.chatText.isEmpty()) return;
                sendMessage = this.chatText.replace("%player%", playerName).replace("%server%", server) + message;
            } else {
                // "<gray><u><click:run_command:'/server %server%'><hover:show_text:'Click to switch.'>[%server%]</hover></click></u> <%player%> "
                sendMessage = String.join(""
                        , "<gray><u><click:run_command:'/server "
                        , server
                        , "'><hover:show_text:'Click to switch.'>["
                        , server
                        , "]</hover></click></u> <"
                        , playerName
                        , "> "
                        , message
                );
            }
        } else {
            sendMessage = "<" + player.getUsername() + "> " + message;
        }
        // send message to other server
        for (RegisteredServer s : this.proxyServer.getAllServers()) {
            if (!Objects.equals(s, player.getCurrentServer().get().getServer())) {
                s.sendMessage(Deserializer.miniMessage(sendMessage));
            }
        }
    }
}



