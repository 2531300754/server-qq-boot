package com.djj.serverqqbot.enums;

import java.util.Arrays;

public enum Opcode {
    DISPATCH(0),

    HEARTBEAT(1),

    IDENTIFY(2),

    RESUME(6),

    RECONNECT(7),

    INVALID(9),

    HELLO(10),

    HEARTBEAT_ACK(11);
    private Integer code;

    Opcode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public static Opcode getByCode(Integer code) {
        return Arrays.stream(Opcode.values())
                .filter(op -> op.getCode().equals(code))
                .findFirst()
                .get();
    }
}

