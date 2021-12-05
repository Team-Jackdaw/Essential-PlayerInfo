package com.wdrshadow.essentialinfo.module.tablist;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.player.TabListEntry;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.wdrshadow.essentialinfo.EssentialInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.concurrent.TimeUnit;

public class TabList {
    // class server
    private final ProxyServer proxyServer;

    // connect the module to the plugin and server
    public TabList(ProxyServer proxyServer, EssentialInfo plugin) {
        this.proxyServer = proxyServer;
        this.proxyServer.getScheduler().buildTask(plugin, this::update)
                .repeat(50L, TimeUnit.MILLISECONDS).schedule();
    }

    // listener of player login
    @Subscribe
    public void connect(ServerConnectedEvent event) {
        connectUpdate(event);
        connectNote(event);
    }

    // listener of player disconnect
    @Subscribe
    public void disconnect(DisconnectEvent event) {
        remove(event.getPlayer());
        disconnectNote(event);
    }

    // update tab list
    private void connectUpdate(ServerConnectedEvent event) {
        for (Player player : this.proxyServer.getAllPlayers()) {
            for (Player player1 : this.proxyServer.getAllPlayers()) {
                // not the same server && not already exist
                if (!player.getTabList().containsEntry(player1.getUniqueId())) {
                    String serverName = player1.getCurrentServer().isPresent()
                            ? player1.getCurrentServer().get().getServerInfo().getName()
                            : event.getServer().getServerInfo().getName();
                    player.getTabList().addEntry(TabListEntry.builder()
                            .displayName(Component.text("[" + serverName + "] " + player1.getUsername()))
                            .latency((int) player1.getPing())
                            .profile(player1.getGameProfile())
                            .gameMode(0)
                            .tabList(player.getTabList())
                            .build());
                }
            }
        }
    }

    private void update(){
        for (Player player : this.proxyServer.getAllPlayers()) {
            for (Player player1 : this.proxyServer.getAllPlayers()) {
                // not the same server && not already exist
                if (!player.getTabList().containsEntry(player1.getUniqueId())) {
                    if(player1.getCurrentServer().isPresent()){
                    player.getTabList().addEntry(TabListEntry.builder()
                            .displayName(Component.text("["
                                    + player1.getCurrentServer().get().getServerInfo().getName() + "] "
                                    + player1.getUsername()))
                            .latency((int) player1.getPing())
                            .profile(player1.getGameProfile())
                            .gameMode(3)
                            .tabList(player.getTabList())
                            .build());
                    }
                }
            }
        }
    }

    // remove disconnected player from list
    private void remove(Player player) {
        for (Player p : this.proxyServer.getAllPlayers()) {
            if (p.getTabList().containsEntry(player.getUniqueId())) {
                p.getTabList().removeEntry(player.getUniqueId());
            }
        }
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
            sendMessage = player.getUsername() + ": Connect to ["
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
        String sendMessage = player.getUsername() + ": Exit the servers.";
        TextComponent textComponent = Component.text(sendMessage);
        for (RegisteredServer s : this.proxyServer.getAllServers()){
            s.sendMessage(textComponent);
        }
    }
}