package com.jackdaw.essentialinfo.module.rememberMe;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
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

    private UserInfoParser userInfo;


    // connect the module to the plugin and server
    public RememberMe(File parentWorkingDirectory, ProxyServer proxyServer, Logger logger) throws IOException {
        this.workingDirectory = new File(parentWorkingDirectory.getName()+"/user");
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    // open UserInfo file and record a information.
    private UserInfoParser UserInfo(Player player) {
        return new UserInfoParser(workingDirectory, logger, player);
    }

    // listener of player connection and connect to the previous or default server
    @Subscribe
    public void onPlayerLogging(PlayerChooseInitialServerEvent event) {
        String initialServerName = UserInfo(event.getPlayer()).getServer();
        if(initialServerName == null){return;}
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
        UserInfoParser userInfo = UserInfo(event.getPlayer());
        if (!userInfo.isDefaultOn()) {
            userInfo.setServer(event.getServer().getServerInfo().getName());
        }
    }

    // set the default server by command
    // @Subscribe
    // public void onCommandSetDefaultServer(CommandExecuteEvent event) {
    //
    // }
}
