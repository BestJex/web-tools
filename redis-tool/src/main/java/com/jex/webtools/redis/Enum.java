package com.jex.webtools.redis;

import lombok.Getter;
import lombok.Setter;

/**
 * 枚举类
 **/
public enum Enum {
    /**
     * 枚举
     */
    PV("pv", "pv_topic"),
    UV("uv", "uv_topic"),
    NU("nu", "nu_topic");

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private String topic;

    Enum(String key, String topic) {
        this.key = key;
        this.topic = topic;
    }

    public static Enum get(String key) {
        for (Enum c : Enum.values()) {
            if (c.key == key) {
                return c;
            }
        }
        return null;
    }
}
