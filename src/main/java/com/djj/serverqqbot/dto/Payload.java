package com.djj.serverqqbot.dto;

import cn.hutool.json.JSONObject;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
@Builder
public class Payload implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer op;

    private Integer s;

    private String t;

    private JSONObject d;

    public String toJsonString() {
        return new JSONObject(this).toString();
    }
}
