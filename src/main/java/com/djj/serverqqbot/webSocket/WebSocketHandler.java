package com.djj.serverqqbot.webSocket;

import cn.hutool.json.JSONObject;
import com.djj.serverqqbot.dto.Bot;
import com.djj.serverqqbot.dto.Message;
import com.djj.serverqqbot.dto.Payload;
import com.djj.serverqqbot.enums.ActionType;
import com.djj.serverqqbot.enums.Opcode;
import com.djj.serverqqbot.event.ActionEvent;
import com.djj.serverqqbot.event.MessageEvent;
import com.djj.serverqqbot.properties.GuildProperties;
import com.djj.serverqqbot.service.GPTHandler;
import com.djj.serverqqbot.util.BotUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private GuildProperties guildProperties;

    private volatile WebSocketSession session;

    @Autowired
    private GPTHandler gptHandler;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    private Bot bot = Bot.builder()
            .isStop(false)
            .seq(0)
            .build();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("连接成功");
        this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("收到Socket消息：" + message.getPayload());
        this.session = session;
        Payload payload = new JSONObject(message.getPayload()).toBean(Payload.class);
        Opcode opcode = Opcode.getByCode(payload.getOp());
        System.out.println(opcode);
        switch (opcode) {
            case HELLO:
                helloOperateHandler(payload);
                break;
            case DISPATCH:
                dispatchOperateHandler(payload);
                break;
            case HEARTBEAT_ACK:
                heartbeatAckOperateHandler();
                break;
            case RECONNECT:
                reconnectionOperateHandler();
                break;
        }
    }

    private void helloOperateHandler(Payload payload) {
        initHeartbeatInterval(payload);
        sendSocketAuthInfo();
    }

    private void dispatchOperateHandler(Payload payload) {
        Integer seq = payload.getS();
        if (seq != null && seq > 0) {
            this.bot.setSeq(seq);
        }
        String event = payload.getT();
        if (event.equals("READY")) {
            JSONObject data = payload.getD();
            JSONObject user = data.getJSONObject("user");
            this.bot.setBotId(user.getStr("id"));
            this.bot.setBotName(user.getStr("username"));
            this.bot.setSessionId(user.getStr("session_id"));
            if (!this.bot.getIsStop()) {
                handlerHeartbeatInterval();
            }
            this.bot.setIsStop(false);
        } else if (event.equals("DIRECT_MESSAGE_CREATE")) {
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setBot(this.bot);
            messageEvent.setMessage(payload.getD().toBean(Message.class));

            System.out.println(messageEvent);
            gptHandler.reply(messageEvent);

        }
    }

    private void heartbeatAckOperateHandler() {
        log.info("接收到服务端心跳回复");
    }

    private void reconnectionOperateHandler() {
        log.info("服务端通知客户端重连");
        this.bot.setIsStop(true);
        // 发布 ActionEvent 事件
        ActionEvent actionEvent =  ActionEvent.builder().actionType(ActionType.RECONNECTION).build();
        eventPublisher.publishEvent(actionEvent);
    }

    /**
     * 处理程序心跳间隔
     */
    private void handlerHeartbeatInterval() {
        Thread heartbeatThread = new Thread(() -> {
            while (true) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("op", 1);
                jsonObject.set("d", this.bot.getSeq());
                try {
                    Thread.sleep(this.bot.getHeartbeatInterval());
                    if (!this.bot.getIsStop()) {
                        sendMessage(jsonObject.toString());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
        heartbeatThread.setDaemon(true);//守护线程
        heartbeatThread.setName("HeartbeatThread");
        heartbeatThread.start();
    }

    private void sendMessage(String message) {
        synchronized (this) {
            if (this.session != null && this.session.isOpen()) {
                try {
                    this.session.sendMessage(new TextMessage(message));
                } catch (IllegalStateException e) {
                    log.warn("试图在无效状态下发送消息: " + e.getMessage());
                } catch (Exception e) {
                    log.error("发送WebSocket消息失败: " + e.getMessage(), e);
                }
            } else {
                log.warn("WebSocket会话未打开，无法发送消息");
            }
        }
    }

    @SneakyThrows
    private void sendSocketAuthInfo() {
        JSONObject authData = new JSONObject();
        authData.set("token", BotUtil.buildAuthToken(guildProperties.getBotAppId(), guildProperties.getBotToken()));
        authData.set("intents", BotUtil.computeIntents(guildProperties.getIntents()));
        authData.set("shard", guildProperties.getShard());
        Payload payload = Payload.builder()
                .op(Opcode.IDENTIFY.getCode())
                .d(authData)
                .build();
        sendMessage(payload.toJsonString());
    }

    private void initHeartbeatInterval(Payload payload) {
        Long interval = payload.getD().getLong("heartbeat_interval");
        log.info("heartbeat_time:" + interval);
        long heartbeatInterval = new BigDecimal(interval)
                .divide(new BigDecimal(2), 0, RoundingMode.HALF_DOWN)
                .longValue();//将返回的心跳间隔除以二
        log.info("heartbeat_interval:" + heartbeatInterval);
        this.bot.setHeartbeatInterval(heartbeatInterval);
    }
}
