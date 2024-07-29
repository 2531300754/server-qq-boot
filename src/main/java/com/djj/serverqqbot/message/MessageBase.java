package com.djj.serverqqbot.message;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
public class MessageBase implements Serializable {
    private static final long serialVersionUID = 1L;

    private String msg_id;

    public String toJsonString() {
        return new JSONObject(this).toString();
    }
}