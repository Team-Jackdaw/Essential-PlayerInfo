package com.jackdaw.essentialinfo;

import com.google.inject.Inject;
import com.jackdaw.essentialinfo.auxiliary.configuration.SettingManager;
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
import java.util.concurrent.TimeUnit;

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
        // load setting config
        SettingManager setting = getSettingManager();
        if (setting == null) {
            logger.error("Main: Can't load config file.");
            return;
        }
        if (setting.isTabListEnabled()) {
            TabList tabList = new TabList(proxyServer, setting);
            this.proxyServer.getEventManager().register(this, tabList);
            this.proxyServer.getScheduler().buildTask(this, tabList::pingUpdate)
                    .repeat(50L, TimeUnit.MILLISECONDS).schedule();
            logger.info("Main: Loaded TabList.");
        }

        if (setting.isMessageEnabled()) {
            this.proxyServer.getEventManager().register(
                    this, new Message(this.proxyServer, logger, setting));
            logger.info("Main: Loaded Message.");
        }

        if (setting.isPingListEnabled()) {
            this.proxyServer.getEventManager().register(this, new PingList(this.proxyServer));
            logger.info("Main: Loaded PingList.");
        }

        if (setting.isConnectionTipsEnabled()) {
            this.proxyServer.getEventManager().register(this, new ConnectionTips(this.proxyServer, setting));
            logger.info("Main: Loaded ConnectionTips.");
        }

        if (setting.isRememberMeEnabled()) {
            try {
                this.proxyServer.getEventManager().register(this, new RememberMe(dataDirectory.toFile(), this.proxyServer, logger));
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
            logger.info("Main: Loaded RememberMe.");
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

