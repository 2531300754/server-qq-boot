package com.djj.serverqqbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Author：DJJ
 * Date：2024/7/28
 */

@Data
@Component
@ConfigurationProperties(prefix = "chatgpt")
public class ChatGPTProp {
    // 代理配置
    private ProxyConfig proxy;
    // apiKey
    private List<String> apiKeys;
    // apiHost
    private String apiHost;

    @Data
    static class ProxyConfig {
        private boolean enable;
        private String type;
        private String host;
        private int port;
    }
}
