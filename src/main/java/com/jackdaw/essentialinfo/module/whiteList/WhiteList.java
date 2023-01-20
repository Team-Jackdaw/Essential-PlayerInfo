package com.jackdaw.essentialinfo.module.whiteList;

import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.module.AbstractComponent;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class WhiteList extends AbstractComponent {

    private final File workingDirectory;

    /**
     * Instantiates a new Abstract component.
     *
     * @param proxyServer     the proxy server, is expected to be provided by Velocity.
     * @param logger          the logger, is expected to be provided by Velocity.
     * @param velocityDataDir the velocity data directory, is expected to be provided by Velocity.
     * @param setting         the setting, is expected to be provided by the Main Class.
     */
    public WhiteList(ProxyServer proxyServer, Logger logger, Path velocityDataDir, SettingManager setting) {
        super(proxyServer, logger, velocityDataDir, setting);
        this.workingDirectory = new File(velocityDataDir.toFile(), "user");
        commandSet();
    }

    //command manager, register the command "wl".
    // need refactored!
    private void commandSet() {
        CommandManager commandManager = proxyServer.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("wl").build();
        CommandSet commandSet = new CommandSet(proxyServer, logger, workingDirectory);
        commandManager.register(commandMeta, commandSet);
    }

    @Subscribe
    public void onConnectEvent (ServerConnectedEvent event){
        // when someone connect to server, check if they have the permission.
    }
}
