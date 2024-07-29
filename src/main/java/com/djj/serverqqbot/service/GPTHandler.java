package com.djj.serverqqbot.service;

import cn.hutool.core.collection.CollUtil;
import com.djj.serverqqbot.config.ChatGPTProp;
import com.djj.serverqqbot.event.MessageEvent;
import com.djj.serverqqbot.message.MessageBase;
import com.djj.serverqqbot.message.NormalMessage;
import com.djj.serverqqbot.request.GuildOpenApi;
import com.plexpt.chatgpt.ChatGPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Component
public class GPTHandler {
    @Autowired
    private GuildOpenApi guildOpenApi;
    @Autowired
    private ChatGPT chatGPT;

    public void reply(MessageEvent event) {
        String content = event.getMessage().getContent();
        System.out.println("内容：" + content);
        String replay = chatGPT.chat(content);


        MessageBase messageBase = NormalMessage.builder()
                .msg_id(event.getMessage().getId())
                .content(replay)
                .build();
        guildOpenApi.sendMessage(event.getMessage().getGuild_id(), messageBase);
    }

}
