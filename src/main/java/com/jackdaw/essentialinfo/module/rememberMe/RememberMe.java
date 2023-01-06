package com.jackdaw.essentialinfo.module.rememberMe;

import com.velocitypowered.api.event.Subscribe;
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


    // connect the module to the plugin and server
    public RememberMe(File parentWorkingDirectory, ProxyServer proxyServer, Logger logger) throws IOException {
        this.workingDirectory = new File(parentWorkingDirectory, "user");
        this.proxyServer = proxyServer;
        this.logger = logger;
        checkFolder();
    }

    // check whether the /user folder is existed.
    private void checkFolder(){
        if(!workingDirectory.exists()){
            if(!workingDirectory.mkdir()){
                logger.error("RememberMe: Can't make a new folder.");
            }
        }
    }

    // open UserInfo file and record a information.
    private UserInfoParser userInfo(Player player) {
        return new UserInfoParser(workingDirectory, logger, player);
    }

    // listener of player connection and connect to the previous or default server
    @Subscribe
    public void onPlayerLogging(PlayerChooseInitialServerEvent event) {
        String initialServerName = userInfo(event.getPlayer()).getUserData().getServer();
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
        UserInfoParser userInfo = userInfo(event.getPlayer());
        if (!userInfo.getUserData().getDefaultMde()) {
            userInfo.setUserServer(event.getServer().getServerInfo().getName());
        }
    }

    // set the default server by command
    public void setServer(String serverName, Player player) {
        UserInfoParser userInfo = userInfo(player);
        userInfo.setDefaultMode(true);
        userInfo.setUserServer(serverName);
    }

    // set the default mode by command
    public void setMode(boolean mode, Player player) {
        UserInfoParser userInfo = userInfo(player);
        userInfo.setDefaultMode(mode);
        if (player.getCurrentServer().isPresent()) {
            userInfo.setUserServer(player.getCurrentServer().get().getServerInfo().getName());
        } else userInfo.setUserServer(null);
    }
}
