package com.djj.serverqqbot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author：DJJ
 * Date：2024/7/28
 *
 */
@Data
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    private String joined_at;

    private String nick;

    private List<String> roles;
}
