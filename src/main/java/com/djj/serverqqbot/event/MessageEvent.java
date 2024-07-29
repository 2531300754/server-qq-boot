package com.djj.serverqqbot.event;

import com.djj.serverqqbot.dto.Bot;
import com.djj.serverqqbot.dto.Message;
import lombok.Data;

import java.io.Serializable;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
public class MessageEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Bot bot;

    private String guildName;

    private Message message;
}
