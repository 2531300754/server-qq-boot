package com.djj.serverqqbot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String channel_id;//子频道id

    private String content;

    private String guild_id;//频道id

    private String timestamp;

    private User author;//消息中@的人

    private Member member;//消息创建者的member信息
}

