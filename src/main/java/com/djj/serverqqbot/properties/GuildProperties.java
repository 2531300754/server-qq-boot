package com.djj.serverqqbot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
@Component
@ConfigurationProperties(prefix = "qq-guild")
public class GuildProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    private String baseUrl;

    private String botAppId;

    private String botToken;

    private List<Integer> shard;

    private List<Integer> intents;
}

