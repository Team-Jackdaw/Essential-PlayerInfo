package com.jackdaw.essentialinfo.module.rememberMe;

import com.jackdaw.essentialinfo.API.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

public final class CommandSet implements Command {

    private final RememberMe rememberMe;

    public CommandSet(RememberMe rememberMe){
        this.rememberMe = rememberMe;
    }

    @Override
    public void action(CommandSource commandSource, String[] args) {
        String command = args[1];
        String parameter = args[2];
        Player player = (Player) commandSource;
        if (command.equals("mode")){
            rememberMe.setMode(Boolean.parseBoolean(parameter), player);
        }
        if (command.equals("server")){
            rememberMe.setServer(parameter, player);
        }
    }
}
