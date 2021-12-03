package com.wdrshadow.essentialinfo.module.tablist;

import com.wdrshadow.essentialinfo.EssentialInfo;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.player.TabListEntry;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.UUID;

public class TabList {
    // 服务器类
    private ProxyServer proxyServer;

    // 构造函数，链接服务器类与插件
    public TabList(EssentialInfo plugin, ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    // 玩家加入事件监听器
    @Subscribe
    public void connect(ServerConnectedEvent event) {
        update();
    }

    // 玩家退出事件监听器
    @Subscribe
    public void disconnect(DisconnectEvent event) {
        update();
    }

    // 更新tab列表
    public void update() {
        for (Player player : this.proxyServer.getAllPlayers()) {
            for (Player player1 : this.proxyServer.getAllPlayers()) {
                if (!player.getTabList().containsEntry(player1.getUniqueId())) {
                    player.getTabList().addEntry(
                            TabListEntry.builder()
                                    .displayName(Component.text(player1.getUsername()))
                                    .profile(player1.getGameProfile())
                                    .gameMode(0) // Impossible to get player game mode from proxy, always assume survival
                                    .tabList(player.getTabList())
                                    .build()
                    );
                }
            }

            for (TabListEntry entry : player.getTabList().getEntries()) {
                UUID uuid = entry.getProfile().getId();
                Optional<Player> playerOptional = proxyServer.getPlayer(uuid);
                if (playerOptional.isPresent()) {
                    // Update ping
                    entry.setLatency((int) (player.getPing() * 1000));
                } else {
                    player.getTabList().removeEntry(uuid);
                }
            }
        }
    }
}