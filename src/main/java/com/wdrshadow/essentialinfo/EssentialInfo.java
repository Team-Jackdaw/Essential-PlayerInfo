package com.wdrshadow.essentialinfo;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.wdrshadow.essentialinfo.module.message.Message;
import com.wdrshadow.essentialinfo.module.pinglist.PingList;
import com.wdrshadow.essentialinfo.module.tablist.TabList;
import org.slf4j.Logger;
import com.wdrshadow.essentialinfo.configuration.Setting;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;

// register the plugin
@Plugin(
        id = "essential-info",
        name = "Essential Info",
        version = BuildConstants.VERSION,
        authors = {"WDRshadow"}
)
public class EssentialInfo {

    // generate class server and logger
    @Inject
    private ProxyServer proxyServer;
    @Inject
    private Logger logger;

    // path of the plugin
    @Inject
    private @DataDirectory
    Path dataDirectory;

    // get config
    private Setting setting;

    // tabList module
    private TabList tabList;
    // message module
    private Message message;
    // PingList module
    private PingList pingList;


    // connect to the server and logger
    @Inject
    public EssentialInfo(ProxyServer proxyServer, Logger logger){
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    // register the listeners
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.setting = new Setting(dataDirectory.toFile());

        if (setting.isTabListEnabled()) {
            this.tabList = new TabList(this.proxyServer, this.logger);
            this.proxyServer.getEventManager().register(this, this.tabList);
        }

        if (setting.isMessageEnabled()) {
            this.message = new Message(this.proxyServer, this.logger);
            this.proxyServer.getEventManager().register(this, this.message);
        }

        if (setting.isPingListEnabled()) {
            this.pingList = new PingList(this, this.proxyServer);
            this.proxyServer.getEventManager().register(this, this.pingList);
        }
    }
    }

