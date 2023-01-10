package com.jackdaw.essentialinfo.module;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

/**
 * The abstract class for all modules.
 * <p>
 * To make the injection work properly, write the constractor of the subclass like the following
 * <pre>
 * {@code
 *      @Inject
 *      public TabList(ProxyServer proxyServer, Logger logger,
 *      @VelocityDataDir Path velocityDataDir, SettingManager setting) {
 *          super(proxyServer, logger, velocityDataDir, setting);
 *          ...         // Any other code
 *      }
 * }</pre>
 * <p>
 * Because of the Google Guice, com.google.inject, takes the name AbstractModule
 * This class is then called AbstractComponent instead of that, which is more suitable.
 *
 * @see com.google.inject.Guice
 */
public abstract class AbstractComponent {

    /**
     * The Proxy server.
     */
    protected final ProxyServer proxyServer;
    /**
     * The Logger.
     */
    protected final Logger logger;
    /**
     * The Velocity data directory.
     */
    protected final @VelocityDataDir Path velocityDataDir;
    /**
     * The Setting.
     */
    protected final SettingManager setting;

    /**
     * Instantiates a new Abstract component.
     *
     * @param proxyServer     the proxy server, is expected to be provided by Velocity.
     * @param logger          the logger, is expected to be provided by Velocity.
     * @param velocityDataDir the velocity data directory, is expected to be provided by Velocity.
     * @param setting         the setting, is expected to be provided by the Main Class.
     */
    @Inject
    public AbstractComponent(ProxyServer proxyServer, Logger logger, @VelocityDataDir Path velocityDataDir, SettingManager setting) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.velocityDataDir = velocityDataDir;
        this.setting = setting;
    }

}
