package com.jackdaw.essentialinfo.module.rememberMe;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.auxiliary.serializer.Deserializer;
import com.jackdaw.essentialinfo.auxiliary.userInfo.UserInfoManager;
import com.jackdaw.essentialinfo.module.AbstractComponent;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class CommandSet extends AbstractComponent implements SimpleCommand {

    private final File workingDirectory;

    @Inject
    public CommandSet(ProxyServer proxyServer, Logger logger, Path velocityDataDir, SettingManager setting) {
        super(proxyServer, logger, velocityDataDir, setting);
        this.workingDirectory = new File(velocityDataDir.toFile(), "user");
    }

    @Override
    public void execute(@NotNull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        Player player = (Player) source;
        UserInfoManager userInfoManager = new UserInfoManager(workingDirectory, logger, player);
        Component error = Deserializer.miniMessage(String.join(" "
                , "<dark_gray><yellow>-------------------------------\nThis is RememberMe module of Essential-PlayerInfo plugin.\nSet mode: <light_purple><u><click:suggest_command:'/remember mode '>/remember mode <last, preset></click></u></light_purple>\nSet server: <light_purple><u><click:suggest_command:'/remember server '>/remember server <servername></click></u></light_purple>\nYour default mode is <u><red>"
                , userInfoManager.getUserInfo().getDefaultMode()
                , "</red></u>.\nYour initial server is <red><u>"
                , userInfoManager.getUserInfo().getServer()
                , "</u></red>.\n-------------------------------</yellow></dark_gray>"));
        if (invocation.arguments().length < 2) {
            source.sendMessage(error);
            return;
        }
        String command = args[0];
        String parameter = args[1];
        if (command.equals("mode")) {
            if (parameter.equalsIgnoreCase("preset") || parameter.equalsIgnoreCase("last")) {
                setMode(parameter, player, userInfoManager);
                source.sendMessage(Component.text("Your default mode is set to " + parameter));
                return;
            }
        }
        if (command.equals("server")) {
            RegisteredServer initialServer = this.proxyServer.getAllServers()
                    .stream()
                    .filter(s -> s.getServerInfo().getName().equals(parameter))
                    .findFirst()
                    .orElse(null);
            if (!(initialServer == null)) {
                setServer(parameter, userInfoManager);
                source.sendMessage(Component.text("Your default server is set to " + parameter));
                return;
            }
        }
        source.sendMessage(error);
    }

    @Override
    public @NotNull CompletableFuture<List<String>> suggestAsync(@NotNull Invocation invocation) {
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
    public void setServer(String serverName, @NotNull UserInfoManager userInfoManager) {
        userInfoManager.setDefaultMode("preset");
        userInfoManager.setUserServer(serverName);
    }

    // set the default mode by command
    public void setMode(String mode, @NotNull Player player, @NotNull UserInfoManager userInfoManager) {
        userInfoManager.setDefaultMode(mode);
        if (player.getCurrentServer().isPresent()) {
            userInfoManager.setUserServer(player.getCurrentServer().get().getServerInfo().getName());
        } else userInfoManager.setUserServer(null);
    }
}
