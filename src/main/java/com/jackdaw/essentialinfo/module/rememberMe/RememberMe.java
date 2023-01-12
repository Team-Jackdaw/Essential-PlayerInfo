package com.jackdaw.essentialinfo.module.rememberMe;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.auxiliary.userInfo.UserInfoManager;
import com.jackdaw.essentialinfo.module.AbstractComponent;
import com.jackdaw.essentialinfo.module.VelocityDataDir;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class RememberMe extends AbstractComponent {
    // class server
    private final File workingDirectory;

    @Inject
    public RememberMe(ProxyServer proxyServer, Logger logger, @VelocityDataDir Path velocityDataDir, SettingManager setting) {
        super(proxyServer, logger, velocityDataDir, setting);
        this.workingDirectory = new File(velocityDataDir.toFile(), "user");
        checkFolder();
        commandSet();
    }

    // check whether the /user folder is existed.
    private void checkFolder(){
        if(!workingDirectory.exists()){
            if(!workingDirectory.mkdir()){
                logger.error("RememberMe: Can't make a new folder.");
            }
        }
    }

    //command manager, register the command "remember".
    private void commandSet() {
        CommandManager commandManager = proxyServer.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("remember").build();
        CommandSet commandSet = new CommandSet(proxyServer, logger, workingDirectory);
        commandManager.register(commandMeta, commandSet);
    }

    // listener of player connection and connect to the previous or default server
    @Subscribe
    public void onPlayerLogging(PlayerChooseInitialServerEvent event) {
        String initialServerName = new UserInfoManager(workingDirectory, logger, event.getPlayer()).getUserInfo().getServer();
        if (initialServerName == null) {
            return;
        }
        RegisteredServer initialServer = this.proxyServer.getAllServers()
                .stream()
                .filter(s -> s.getServerInfo().getName().equals(initialServerName))
                .findFirst()
                .orElse(null);
        if (initialServer == null) return;
        event.setInitialServer(initialServer);
    }

    // listener of player disconnection and remember the last server the player exit
    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        UserInfoManager userInfoManager = new UserInfoManager(workingDirectory, logger, event.getPlayer());
        if (userInfoManager.getUserInfo().getDefaultMode().equals("last")) {
            userInfoManager.setUserServer(event.getServer().getServerInfo().getName());
        }
    }
}
