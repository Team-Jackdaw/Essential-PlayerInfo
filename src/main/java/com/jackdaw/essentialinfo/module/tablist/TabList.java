package com.jackdaw.essentialinfo.module.tablist;

import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.auxiliary.serializer.Deserializer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.player.TabListEntry;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class TabList {
    // class server
    private final ProxyServer proxyServer;
    private final boolean isCustomTextEnabled;
    private final String tabListText;
    private final int displayMode;

    // connect the module to the plugin and server
    public TabList(ProxyServer proxyServer, SettingManager setting) {
        this.proxyServer = proxyServer;
        this.isCustomTextEnabled = setting.isCustomTextEnabled();
        this.tabListText = setting.getTabListText();
        this.displayMode = setting.getTabDisplayMode();
    }

    // listener of player login
    @Subscribe
    public void connect(ServerConnectedEvent event) {
        connectUpdate(event);
    }

    // listener of player disconnect
    @Subscribe
    public void disconnect(@NotNull DisconnectEvent event) {
        disconnectUpdate(event.getPlayer());
    }

    // pingUpdate tab list (!! not work when changing the server)
    private void connectUpdate(@NotNull ServerConnectedEvent event) {
        Player playerOfEvent = event.getPlayer();
        // Update list of current players
        for (Player toPlayer : this.proxyServer.getAllPlayers()) {
            String serverName = event.getServer().getServerInfo().getName();
            addTabListEntry(toPlayer, playerOfEvent, serverName);
        }
        // Update list of new connecting player
        for (Player fromPlayer : this.proxyServer.getAllPlayers()) {
            if (fromPlayer.getCurrentServer().isPresent()) {
                String serverName = fromPlayer.getCurrentServer().get().getServerInfo().getName();
                addTabListEntry(playerOfEvent, fromPlayer, serverName);
            }
        }
    }

    // add TabList entry
    private void addTabListEntry(@NotNull Player toPlayer, @NotNull Player fromPlayer, String serverName) {
        if (toPlayer.getUniqueId().equals(fromPlayer.getUniqueId())) return;
        if (toPlayer.getTabList().containsEntry(fromPlayer.getUniqueId())) {
            TabListEntry tabListEntry = findTabListEntry(toPlayer, fromPlayer);
            if (!(tabListEntry == null)) {
                tabListEntry.setDisplayName(getDisplayName(serverName, fromPlayer));
                return;
            }
        }
        toPlayer.getTabList().addEntry(TabListEntry.builder()
                .displayName(getDisplayName(serverName, fromPlayer))
                .latency((int) fromPlayer.getPing())
                .profile(fromPlayer.getGameProfile())
                .gameMode(displayMode)
                .tabList(toPlayer.getTabList())
                .build());
    }

    // remove disconnected player from list
    private void disconnectUpdate(Player fromPlayer) {
        for (Player toPlayer : this.proxyServer.getAllPlayers()) {
            if (toPlayer.getTabList().containsEntry(fromPlayer.getUniqueId())) {
                toPlayer.getTabList().removeEntry(fromPlayer.getUniqueId());
            }
        }
    }

    // normal pingUpdate, public method used for registering the scheduler in plugin.
    public void pingUpdate() {
        for (Player toPlayer : this.proxyServer.getAllPlayers())
            for (Player fromPlayer : this.proxyServer.getAllPlayers()) {
                TabListEntry tabListEntry = findTabListEntry(toPlayer, fromPlayer);
                if (!(tabListEntry == null)) {
                    tabListEntry.setLatency((int) fromPlayer.getPing());
                }
            }
    }

    // get display name
    private @NotNull Component getDisplayName(String serverName, Player fromPlayer) {
        String displayMessage;
        if (this.isCustomTextEnabled) {
            if (this.tabListText.isEmpty()) {
                displayMessage = fromPlayer.getUsername();
            } else
                displayMessage = this.tabListText.replace("%player%", fromPlayer.getUsername()).replace("%server%", serverName);
        } else {
            displayMessage = "[" + serverName + "] " + fromPlayer.getUsername();
        }
        return Deserializer.deserialize(displayMessage);
    }

    // filter of TabListEntry of fromPlayer in toPlayer
    private TabListEntry findTabListEntry(@NotNull Player toPlayer, @NotNull Player fromPlayer) {
        return toPlayer.getTabList().getEntries()
                .stream()
                .filter(t -> t.getProfile().getId().equals(fromPlayer.getGameProfile().getId()))
                .findAny()
                .orElse(null);
    }
}