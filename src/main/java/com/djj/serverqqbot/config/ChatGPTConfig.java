package com.djj.serverqqbot.config;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.util.Proxys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.Proxy;

@Slf4j
@Component
public class ChatGPTConfig {

    @Autowired
    private ChatGPTProp chatGPTProp;

    private Proxy proxy;

    @PostConstruct
    private void initProxy() {
        ChatGPTProp.ProxyConfig pdata = chatGPTProp.getProxy();
        if (!pdata.isEnable()) {
            proxy = null;
        }

        log.info("开启代理，代理类型：{}，代理服务器：{}，代理端口：{}。", pdata.getType(), pdata.getHost(), pdata.getPort());
        if ("http".equals(pdata.getType())) {
            proxy = Proxys.http(pdata.getHost(), pdata.getPort());
        } else {
            proxy = Proxys.socks5(pdata.getHost(), pdata.getPort());
        }
    }

    @Bean
    public ChatGPT chatGPT() {
        // 初始化 ChatGPT 对象
        return ChatGPT.builder()
                .apiKeyList(chatGPTProp.getApiKeys())
                .timeout(900)
                .proxy(proxy)
                .apiHost(chatGPTProp.getApiHost())
                .build()
                .init();
    }

    @Bean
    public ChatGPTStream chatGPTStream() {
        // 初始化 ChatGPTStream 对象
        return ChatGPTStream.builder()
                .timeout(600)
                .apiKeyList(chatGPTProp.getApiKeys())
                .proxy(proxy)
                .apiHost(chatGPTProp.getApiHost())
                .build()
                .init();
    }

}
