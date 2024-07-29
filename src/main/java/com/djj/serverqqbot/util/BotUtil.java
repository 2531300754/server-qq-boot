package com.djj.serverqqbot.util;

import java.util.List;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
public class BotUtil {

    public static String buildAuthToken(String botId, String token) {
        return "Bot ".concat(botId).concat(".").concat(token);
    }

    /**
     * 计算订阅事件
     *
     * @param intents  intents
     * @return int
     */
    public static int computeIntents(List<Integer> intents) {
        int intentsValue = 0;
        for (int i = 0; i < intents.size(); i++) {
            intentsValue = intentsValue|1<<intents.get(i);
        }
        return intentsValue;
    }
}

