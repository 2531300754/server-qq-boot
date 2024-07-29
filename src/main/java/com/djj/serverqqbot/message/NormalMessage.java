package com.djj.serverqqbot.message;

import lombok.Builder;
import lombok.Data;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
public class NormalMessage extends MessageBase {
    private static final long serialVersionUID = 1L;

    private String content;

    @Builder
    public NormalMessage(String msg_id, String content) {
        super.setMsg_id(msg_id);
        this.content = content;
    }
    public static NormalMessage buildTextMessage(String msg_id, String content) {
        return NormalMessage.builder()
                .msg_id(msg_id)
                .content(content)
                .build();
    }
}

