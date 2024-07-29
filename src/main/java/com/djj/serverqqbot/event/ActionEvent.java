package com.djj.serverqqbot.event;

import com.djj.serverqqbot.enums.ActionType;
import lombok.Builder;
import lombok.Data;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
@Builder
public class ActionEvent {

    private ActionType actionType;
}
