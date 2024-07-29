package com.djj.serverqqbot.request;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.djj.serverqqbot.message.MessageBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Slf4j
@Component
public class GuildOpenApi {
    @Autowired
    private GuildRestTemplate guildRestTemplate;

    public String getSocketUrl() {
        String result = guildRestTemplate.exchange("/gateway", HttpMethod.GET);
        if (StrUtil.isBlank(result)) {
            throw new RuntimeException("WebSocket地址获取失败");
        }
        return new JSONObject(result).getStr("url");
    }


    /**
     * 发送消息
     *
     * @param guildId     频道id
     * @param messageBase 消息
     */
    public void sendMessage(String guildId, MessageBase messageBase) {
        guildRestTemplate.postForBodyJson("/dms/" + guildId + "/messages", messageBase.toJsonString());
    }


}
