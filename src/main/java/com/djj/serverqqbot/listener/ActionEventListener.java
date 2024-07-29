package com.djj.serverqqbot.listener;

import com.djj.serverqqbot.enums.ActionType;
import com.djj.serverqqbot.event.ActionEvent;
import com.djj.serverqqbot.webSocket.WebSocketStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Component
public class ActionEventListener {
    @Autowired
    private WebSocketStarter webSocketStarter;

    @EventListener(ActionEvent.class)
    public void handlerResult(ActionEvent actionEvent) {
        if (ActionType.RECONNECTION.equals(actionEvent.getActionType())) {
            webSocketStarter.reconnection();
        }
    }
}
