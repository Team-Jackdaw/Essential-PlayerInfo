package com.jackdaw.essentialinfo.module.rememberMe;

import com.jackdaw.essentialinfo.auxiliary.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class CommandSet implements Command {

    private final RememberMe rememberMe;
    private final ProxyServer proxyServer;

    public CommandSet(ProxyServer proxyServer, RememberMe rememberMe) {
        this.proxyServer = proxyServer;
        this.rememberMe = rememberMe;
    }

    @Override
    public void action(CommandSource commandSource, String[] args) {
        String command = args[0];
        String parameter = args[1];
        Player player = (Player) commandSource;
        if (command.equals("mode")) {
            rememberMe.setMode(Boolean.parseBoolean(parameter), player);
        }
        if (command.equals("server")) {
            rememberMe.setServer(parameter, player);
        }
    }

    // still have bug, but the command work.
    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        if (invocation.arguments().length <= 1) {
            if(invocation.arguments().length == 1){
                if(invocation.arguments()[0].equals("server")){
                    return CompletableFuture.completedFuture(proxyServer
                            .getAllServers()
                            .stream()
                            .map(registeredServer -> registeredServer
                                    .getServerInfo()
                                    .getName())
                            .filter(s -> s.startsWith(invocation.arguments()[0]))
                            .collect(Collectors.toList()));
                }
                if(invocation.arguments()[0].equals("mode")){
                    return CompletableFuture.completedFuture(List.of("true", "false")
                            .stream()
                            .filter(s -> s.startsWith(invocation.arguments()[0]))
                            .collect(Collectors.toList()));
                }
            }
            return CompletableFuture.completedFuture(List.of("server", "mode"));
        } else return CompletableFuture.completedFuture(List.of());
    }
}
