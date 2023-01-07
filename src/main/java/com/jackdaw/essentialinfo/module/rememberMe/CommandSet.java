package com.jackdaw.essentialinfo.module.rememberMe;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class CommandSet implements SimpleCommand {

    private final RememberMe rememberMe;
    private final ProxyServer proxyServer;

    public CommandSet(ProxyServer proxyServer, RememberMe rememberMe) {
        this.proxyServer = proxyServer;
        this.rememberMe = rememberMe;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (invocation.arguments().length < 2) {
            source.sendMessage(Component.text(String.join(" "
                    , "This is RememberMe module of Essential-PlayerInfo plugin. \n"
                    , "You can use `/remember mode <true, false>` or \n "
                    , "`/remember server <servername>` to set your default server.")));
            return;
        }
        String command = args[0];
        String parameter = args[1];
        Player player = (Player) source;
        if (command.equals("mode")) {
            rememberMe.setMode(Boolean.parseBoolean(parameter), player);
        }
        if (command.equals("server")) {
            rememberMe.setServer(parameter, player);
        }
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
            if(currentArgs[0].equals("server")){
                suggest = proxyServer
                        .getAllServers()
                        .stream()
                        .map(registeredServer -> registeredServer
                                .getServerInfo()
                                .getName())
                        .collect(Collectors.toList());
            } else if(currentArgs[0].equals("mode")){
                suggest = List.of("true", "false");
            } else suggest = List.of();

            return CompletableFuture.completedFuture(suggest
                    .stream()
                    .filter(s -> s.startsWith(invocation.arguments()[1]))
                    .collect(Collectors.toList()));
        } else return CompletableFuture.completedFuture(List.of());
    }
}
