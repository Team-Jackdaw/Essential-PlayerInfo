package com.jackdaw.essentialinfo.module.whiteList;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class CommandSet implements SimpleCommand {

    private final ProxyServer proxyServer;
    private final Logger logger;
    private final File workingDirectory;

    // need to refactored.
    public CommandSet(ProxyServer proxyServer, Logger logger, File workingDirectory) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.workingDirectory = workingDirectory;
    }

    @Override
    public void execute(Invocation invocation) {
        // used command to set the whitelist
    }

    @Override
    public boolean hasPermission (Invocation invocation){
        // check whether have permission to run command
        return invocation.source().hasPermission("essinfo.wl");
    }

    @Override
    public @NotNull CompletableFuture<List<String>> suggestAsync(@NotNull Invocation invocation){
        // return suggestions list.
        return null;
    }
}
