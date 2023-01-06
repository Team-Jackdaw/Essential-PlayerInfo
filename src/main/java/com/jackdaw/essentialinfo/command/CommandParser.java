package com.jackdaw.essentialinfo.command;

import com.jackdaw.essentialinfo.module.rememberMe.RememberMe;
import com.velocitypowered.api.proxy.Player;

public class CommandParser {

    public static void commandSelect(String[] args, Player player) {
        String command = args[0];
        String parameter = args[1];
        if (command.equals("server")) {
        } else if (command.equals("mode")) {
        }
    }

}
