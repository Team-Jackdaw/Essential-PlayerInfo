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

// 注册插件
@Plugin(
        id = "essential-info",
        name = "Essential Info",
        version = BuildConstants.VERSION,
        authors = {"WDRshadow"}
)
public class EssentialInfo {

    // 服务器类以及日志接口
    @Inject
    private ProxyServer proxyServer;
    @Inject
    private Logger logger;

    // 插件目录
    @Inject
    private @DataDirectory
    Path dataDirectory;

    // 获取设置
    @Inject
    private Setting setting;

    // tabList模块
    @Inject
    private TabList tabList;
    // 消息模块
    @Inject
    private Message message;
    // PingList模块
    @Inject
    private PingList pingList;


    // 构造函数，链接服务器与日志
    @Inject
    public EssentialInfo(ProxyServer proxyServer, Logger logger){
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    // 注册事件侦听器
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.setting = new Setting(dataDirectory.toFile());

        if (setting.isTabListEnabled()) {
            this.tabList = new TabList(this, this.proxyServer);
            this.proxyServer.getEventManager().register(this, this.tabList);
        }

        if (setting.isMessageEnabled()) {
            this.message = new Message(this, this.proxyServer);
            this.proxyServer.getEventManager().register(this, this.message);
        }

        if (setting.isPingListEnabled()) {
            this.pingList = new PingList(this, this.proxyServer);
            this.proxyServer.getEventManager().register(this, this.pingList);
        }
    }
    }

