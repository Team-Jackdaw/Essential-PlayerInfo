package com.jackdaw.essentialinfo.module;

import com.google.inject.AbstractModule;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Provide the injection config for modules.
 *
 * Use for creating injector by the caller.
 */
public class JackdawModule extends AbstractModule {

    private final ProxyServer proxyServer;
    private final Logger logger;
    private final Path dataDirectory;
    private final SettingManager setting;

    /**
     * Instantiates a new Jackdaw module.
     *
     * @param proxyServer   the proxy server, expect to be provided by velocity.
     * @param logger        the logger, expect to be provided by velocity.
     * @param dataDirectory the data directory, expect to be provided by velocity.
     * @param setting       the setting, expect to be provided by the Main Class, i.e. EssentialInfo.
     */
    public JackdawModule(ProxyServer proxyServer, Logger logger, Path dataDirectory, SettingManager setting) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.setting = setting;
    }

    @Override
    protected void configure() {
        bind(ProxyServer.class).toInstance(proxyServer);
        bind(Logger.class).toInstance(logger);
        bind(Path.class).annotatedWith(VelocityDataDir.class).toInstance(dataDirectory);
        bind(SettingManager.class).toInstance(setting);
    }
}

