package com.wdrshadow.essentialinfo;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.wdrshadow.essentialinfo.configuration.SettingManager;
import com.wdrshadow.essentialinfo.module.message.Message;
import com.wdrshadow.essentialinfo.module.pinglist.PingList;
import com.wdrshadow.essentialinfo.module.tablist.TabList;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

// register the plugin
@Plugin(
        id = "essential-info",
        name = "Essential Info",
        version = BuildConstants.VERSION,
        authors = {"WDRshadow"}
)
public class EssentialInfo {

    // generate instance server and logger
    @Inject
    private ProxyServer proxyServer;
    @Inject
    private Logger logger;

    // path of the plugin
    @Inject
    private @DataDirectory
    Path dataDirectory;

    // get config
    private SettingManager settingManager;

    // connect to the server and logger
    @Inject
    public EssentialInfo(ProxyServer proxyServer, Logger logger){
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    // register the listeners
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        try
        {
        this.settingManager = new SettingManager(dataDirectory.toFile());
        }
        catch (IOException e)
        {
            System.out.println("IO Error");
            e.getStackTrace();
        }
        if (settingManager.isTabListEnabled()) {
            this.proxyServer.getEventManager().register(this, new TabList(this.proxyServer, this));
            logger.info("Loaded TabList.");
        }

        if (settingManager.isMessageEnabled()) {
            this.proxyServer.getEventManager().register(this, new Message(this.proxyServer));
            logger.info("Loaded Message.");
        }

        if (settingManager.isPingListEnabled()) {
            this.proxyServer.getEventManager().register(this, new PingList(this.proxyServer));
            logger.info("Loaded PingList.");
        }
    }
    }

