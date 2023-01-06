package com.jackdaw.essentialinfo.API.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;

public interface Command extends SimpleCommand {

    @Override
    default void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (invocation.arguments().length < 2) {
            source.sendMessage(Component.text(String.join(" ", "You need at least 2 parameter to run the command.")));
            return;
        }
        action(source, args);
    }

    /**
     * This is the action after the command has been sent and execute.
     * @param commandSource Command source.
     * @param args Command parameters.
     */
    void action(final CommandSource commandSource, final String[] args);

}
