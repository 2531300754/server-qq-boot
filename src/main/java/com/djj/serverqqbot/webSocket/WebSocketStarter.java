package com.djj.serverqqbot.webSocket;

import cn.hutool.core.util.StrUtil;
import com.djj.serverqqbot.request.GuildOpenApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Slf4j
@Component
public class WebSocketStarter implements SmartInitializingSingleton {
    @Autowired
    private GuildOpenApi guildRestApi;
    @Autowired
    private WebSocketHandler webSocketHandler;
    private WebSocketConnectionManager webSocketConnectionManager;

    @Override
    public void afterSingletonsInstantiated() {
        log.info("init");
        this.initSocketConnection();
    }

    public void reconnection() {
        log.info("reconnection");
        webSocketConnectionManager.stop();
        this.initSocketConnection();
    }

    @SneakyThrows
    private void initSocketConnection() {
        String socketUrl = null;
        do {
            try {
                socketUrl = guildRestApi.getSocketUrl();
                log.info("socket url:" + socketUrl);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            Thread.sleep(3000L);
        } while (StrUtil.isBlank(socketUrl));
        StandardWebSocketClient socketClient = new StandardWebSocketClient();
        webSocketConnectionManager = new WebSocketConnectionManager(socketClient, webSocketHandler, socketUrl);
        webSocketConnectionManager.start();
    }
}

