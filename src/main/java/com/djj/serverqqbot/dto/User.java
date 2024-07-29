package com.djj.serverqqbot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String avatar;

    private Boolean bot;

    private String id;

    private String username;
}

