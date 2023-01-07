package com.jackdaw.essentialinfo.module.rememberMe;

import com.jackdaw.essentialinfo.auxiliary.serializer.Deserializer;
import com.jackdaw.essentialinfo.auxiliary.userInfo.UserInfoManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class CommandSet implements SimpleCommand {

    private final ProxyServer proxyServer;
    private final Logger logger;
    private final File workingDirectory;

    public CommandSet(ProxyServer proxyServer, Logger logger, File workingDirectory) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.workingDirectory = workingDirectory;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        Component error = Deserializer.deserialize(String.join(" "
                , "&7This is RememberMe module of Essential-PlayerInfo plugin. \n"
                , "Set mode: `/remember mode <last, preset>`\n"
                , "Set server: `/remember server <servername>`"));
        if (invocation.arguments().length < 2) {
            source.sendMessage(error);
            return;
        }
        String command = args[0];
        String parameter = args[1];
        Player player = (Player) source;
        if (command.equals("mode")) {
            if (parameter.equalsIgnoreCase("preset") || parameter.equalsIgnoreCase("last")) {
                setMode(parameter, player);
                source.sendMessage(Component.text("Your default mode is set to " + parameter));
                return;
            }
        }
        if (command.equals("server")) {
            for(RegisteredServer server : proxyServer.getAllServers()){
                if(server.getServerInfo().getName().equals(parameter)){
                    setServer(parameter, player);
                    source.sendMessage(Component.text("Your default server is set to " + parameter));
                    return;
                }
            }
        }
        source.sendMessage(error);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        String[] currentArgs = invocation.arguments();
        List<String> suggest;
        if (currentArgs.length == 0) {
            return CompletableFuture.completedFuture(List.of("server", "mode"));
        } else if (currentArgs.length == 1) {
            suggest = List.of("server", "mode");
            return CompletableFuture.completedFuture(suggest
                    .stream()
                    .filter(s -> s.startsWith(invocation.arguments()[0]))
                    .collect(Collectors.toList()));
        } else if(currentArgs.length == 2) {
            if (currentArgs[0].equals("server")) {
                suggest = proxyServer
                        .getAllServers()
                        .stream()
                        .map(registeredServer -> registeredServer
                                .getServerInfo()
                                .getName())
                        .collect(Collectors.toList());
            } else if (currentArgs[0].equals("mode")) {
                suggest = List.of("last", "preset");
            } else suggest = List.of();

            return CompletableFuture.completedFuture(suggest
                    .stream()
                    .filter(s -> s.startsWith(invocation.arguments()[1]))
                    .collect(Collectors.toList()));
        } else return CompletableFuture.completedFuture(List.of());
    }

    // set the default server by command
    public void setServer(String serverName, Player player) {
        UserInfoManager userInfoManager = new UserInfoManager(workingDirectory, logger, player);
        userInfoManager.setDefaultMode("preset");
        userInfoManager.setUserServer(serverName);
    }

    // set the default mode by command
    public void setMode(String mode, Player player) {
        UserInfoManager userInfoManager = new UserInfoManager(workingDirectory, logger, player);
        userInfoManager.setDefaultMode(mode);
        if (player.getCurrentServer().isPresent()) {
            userInfoManager.setUserServer(player.getCurrentServer().get().getServerInfo().getName());
        } else userInfoManager.setUserServer(null);
    }
}
