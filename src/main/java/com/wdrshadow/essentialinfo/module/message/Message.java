package com.wdrshadow.essentialinfo.module.message;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.wdrshadow.essentialinfo.EssentialInfo;
import com.velocitypowered.api.proxy.server.QueryResponse;

import java.util.Collection;

public class Message {
    // 服务器类
    private ProxyServer proxyServer;

    // 构造函数，链接服务器类与插件
    public Message(EssentialInfo plugin, ProxyServer proxyServer){
        this.proxyServer = proxyServer;
    }

    // 玩家聊天事件侦测器
    @Subscribe(order = PostOrder.EARLY)
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        broadcast(player, message);
    }

    // 玩家消息广播
    public void broadcast(Player player, String message){
        // 获取除本人外的在线玩家列表
        Collection<Player> players = proxyServer.getAllPlayers();
        players.remove(player);
        // 向玩家列表中的玩家发送消息
        for(Player p: players){
            p.spoofChatInput("["+player.getUsername()+"]:"+ message);
        }

    }
}

