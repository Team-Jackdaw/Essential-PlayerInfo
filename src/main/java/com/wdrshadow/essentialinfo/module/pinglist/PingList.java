package com.wdrshadow.essentialinfo.module.pinglist;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.wdrshadow.essentialinfo.EssentialInfo;
import org.slf4j.Logger;

public class PingList {
    // 服务器类
    private ProxyServer proxyServer;

    // 构造函数，链接服务器类与插件
    public PingList(EssentialInfo plugin, ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    // Ping事件侦测器、行为触发
    @Subscribe
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getPing();
        ServerPing.SamplePlayer[] playerInfo = proxyServer.getAllPlayers().stream().map(player -> new ServerPing.SamplePlayer(player.getUsername(), player.getUniqueId())).toArray(ServerPing.SamplePlayer[]::new);
        ServerPing newResponse = response.asBuilder().samplePlayers(playerInfo).build();
        event.setPing(newResponse);
    }
}
