package com.jackdaw.essentialinfo.module.pinglist;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.module.AbstractComponent;
import com.jackdaw.essentialinfo.module.VelocityDataDir;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import org.slf4j.Logger;

import java.nio.file.Path;

public class PingList extends AbstractComponent {

    @Inject
    public PingList(ProxyServer proxyServer, Logger logger, @VelocityDataDir Path velocityDataDir, SettingManager setting) {
        super(proxyServer, logger, velocityDataDir, setting);
    }

    // listener of player ping
    @Subscribe
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getPing();
        ServerPing.SamplePlayer[] playerInfo = proxyServer.getAllPlayers().stream().map(player -> new ServerPing
                .SamplePlayer(player.getUsername(), player.getUniqueId())).toArray(ServerPing.SamplePlayer[]::new);
        ServerPing newResponse = response.asBuilder().samplePlayers(playerInfo).build();
        event.setPing(newResponse);
    }
}
