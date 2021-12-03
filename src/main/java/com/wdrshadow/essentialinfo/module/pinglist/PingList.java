package com.wdrshadow.essentialinfo.module.pinglist;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.wdrshadow.essentialinfo.EssentialInfo;
import org.slf4j.Logger;

public class PingList {
    // class server
    private final ProxyServer proxyServer;

    // connect the module to the plugin and server
    public PingList(EssentialInfo plugin, ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    // listener of player ping
    @Subscribe
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getPing();
        ServerPing.SamplePlayer[] playerInfo = proxyServer.getAllPlayers().stream().map(player -> new ServerPing.SamplePlayer(player.getUsername(), player.getUniqueId())).toArray(ServerPing.SamplePlayer[]::new);
        ServerPing newResponse = response.asBuilder().samplePlayers(playerInfo).build();
        event.setPing(newResponse);
    }
}
