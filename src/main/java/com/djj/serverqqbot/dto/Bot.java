package com.djj.serverqqbot.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
public class Bot implements Serializable {
    private static final long serialVersionUID = 1L;
    private String botId;
    private String botName;
    private String sessionId;
    private Integer seq;
    private Boolean isStop;
    private Long heartbeatInterval;

    @Builder
    public Bot(String botId, String botName, String sessionId, Integer seq, Boolean isStop, Long heartbeatInterval) {
        this.botId = botId;
        this.botName = botName;
        this.sessionId = sessionId;
        this.seq = seq;
        this.isStop = isStop;
        this.heartbeatInterval = heartbeatInterval;
    }
}
