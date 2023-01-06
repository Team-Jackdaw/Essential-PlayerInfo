package com.jackdaw.essentialinfo.API.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Command extends SimpleCommand {

    @Override
    default void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (!hasPermission(invocation)) {
            source.sendMessage(Component.text(String.join(" ", "permission.missing")));
            return;
        }
        if (invocation.arguments().length < 1) {
            source.sendMessage(Component.text(String.join(" ", "parameters.missing")));
            return;
        }
        action(source, args);
    }

    @Override
    default boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("command.e-info");
    }

    @Override
    default CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        if (!hasPermission(invocation)) {
            return CompletableFuture.completedFuture(List.of());
        }

        if (invocation.arguments().length > 0) {
            return CompletableFuture.completedFuture(List.of("server", "mode"));
        }

        return CompletableFuture.completedFuture(List.of());
    }

    /**
     * This is the action after the command has been sent and execute.
     * @param commandSource Command source.
     * @param args Command parameters.
     */
    void action(final CommandSource commandSource, final String[] args);

}
