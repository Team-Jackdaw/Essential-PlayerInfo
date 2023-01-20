package com.jackdaw.essentialinfo.module;

import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.jackdaw.essentialinfo.module.rememberMe.CommandSet;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

public abstract class AbstractCommandSet extends AbstractComponent implements SimpleCommand {

    /**
     * Instantiates a new Abstract component.
     *
     * @param proxyServer     the proxy server, is expected to be provided by Velocity.
     * @param logger          the logger, is expected to be provided by Velocity.
     * @param velocityDataDir the velocity data directory, is expected to be provided by Velocity.
     * @param setting         the setting, is expected to be provided by the Main Class.
     */
    public AbstractCommandSet(ProxyServer proxyServer, Logger logger, Path velocityDataDir, SettingManager setting) {
        super(proxyServer, logger, velocityDataDir, setting);
    }

    //command manager, register the command "remember".
    void commandSet() { }
}
