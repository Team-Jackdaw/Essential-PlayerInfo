package com.wdrshadow.essentialinfo.module.tablist;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.player.TabListEntry;
import com.wdrshadow.essentialinfo.EssentialInfo;
import net.kyori.adventure.text.Component;

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
        update();
    }

    // listener of player disconnect
    @Subscribe
    public void disconnect(DisconnectEvent event) {
        remove(event.getPlayer());
    }

    // update tab list
    private void update() {
        for (Player player : this.proxyServer.getAllPlayers()) {
            for (Player player1 : this.proxyServer.getAllPlayers()) {
                if (!player.getTabList().containsEntry(player1.getUniqueId())) {
                    player.getTabList().addEntry(TabListEntry.builder()
                            .displayName(Component.text(player1.getUsername()))
                            .latency((int) player1.getPing())
                            .profile(player1.getGameProfile())
                            .gameMode(0)
                            .tabList(player.getTabList())
                            .build());
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
}