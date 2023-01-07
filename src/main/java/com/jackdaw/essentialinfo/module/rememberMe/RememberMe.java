package com.jackdaw.essentialinfo.module.rememberMe;

import com.jackdaw.essentialinfo.auxiliary.userInfo.UserInfoManager;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class RememberMe {
    // class server
    private final ProxyServer proxyServer;
    private final Logger logger;
    private final File workingDirectory;


    // connect the module to the plugin and server
    public RememberMe(File parentWorkingDirectory, ProxyServer proxyServer, Logger logger) throws IOException {
        this.workingDirectory = new File(parentWorkingDirectory, "user");
        this.proxyServer = proxyServer;
        this.logger = logger;
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
        if(initialServerName == null){
            return;}
        for (RegisteredServer server : this.proxyServer.getAllServers()) {
            if(server.getServerInfo().getName().equals(initialServerName)){
                event.setInitialServer(server);
                return;
            }
        }
    }

    // listener of player disconnection and remember the last server the player exit
    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        UserInfoManager userInfoManager = new UserInfoManager(workingDirectory, logger, event.getPlayer());
        if (userInfoManager.getUserInfo().getDefaultMde().equals("last")) {
            userInfoManager.setUserServer(event.getServer().getServerInfo().getName());
        }
    }
}
