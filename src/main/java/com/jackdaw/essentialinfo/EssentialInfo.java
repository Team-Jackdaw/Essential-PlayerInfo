package com.jackdaw.essentialinfo;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.configuration.SettingManager;
import com.jackdaw.essentialinfo.module.connectionTips.ConnectionTips;
import com.jackdaw.essentialinfo.module.message.Message;
import com.jackdaw.essentialinfo.module.pinglist.PingList;
import com.jackdaw.essentialinfo.module.rememberMe.RememberMe;
import com.jackdaw.essentialinfo.module.tablist.TabList;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

// register the plugin
@Plugin(
        id = "essential-info",
        name = "Essential Info",
        version = BuildConstants.VERSION,
        authors = {"Team-Jackdaw"}
)
public class EssentialInfo {

    // generate instance server and logger
    @Inject
    private final ProxyServer proxyServer;
    @Inject
    private final Logger logger;

    // path of the plugin
    @Inject
    private @DataDirectory
    Path dataDirectory;

    // connect to the server and logger
    @Inject
    public EssentialInfo(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;
    }


    // register the listeners
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // get config
        SettingManager setting = getSettingManager();
        if (setting == null) return;
        if (setting.isTabListEnabled()) {
            this.proxyServer.getEventManager().register(this, new TabList(this.proxyServer, this, logger));
            logger.info("Loaded TabList.");
        }

        if (setting.isMessageEnabled()) {
            this.proxyServer.getEventManager().register(
                    this, new Message(this.proxyServer, logger, setting));
            logger.info("Loaded Message.");
        }

        if (setting.isPingListEnabled()) {
            this.proxyServer.getEventManager().register(this, new PingList(this.proxyServer));
            logger.info("Loaded PingList.");
        }

        if (setting.isConnectionTipsEnabled()) {
            this.proxyServer.getEventManager().register(this, new ConnectionTips(this.proxyServer, setting));
            logger.info("Loaded ConnectionTips.");
        }

        if (setting.isRememberMeEnabled()) {
            this.proxyServer.getEventManager().register(this, new RememberMe(this.proxyServer, logger));
            logger.info("Loaded RememberMe.");
        }
    }

    @Nullable
    private SettingManager getSettingManager() {
        SettingManager setting;
        try {
            setting = new SettingManager(dataDirectory.toFile(), logger);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return null;
        }
        return setting;
    }
}

