package com.jackdaw.essentialinfo.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.pointer.Pointer;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class Command implements SimpleCommand {


    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();
        // ！！！强制转换接口source未经验证，source接口是player的父类，猜测可以
        CommandParser.commandSelect(args, (Player) source);
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("command.e-info");
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of();
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        return CompletableFuture.completedFuture(List.of("server", "mode"));
    }
}
