package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bing_  @create 2022/4/17-12:37
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    /**
     * 用户状态：0 正常
     */
    NORMAL("0"),

    /**
     * 用户状态：1 禁言
     */
    FORBIDDEN_SPEAK("1"),

    /**
     * 用户状态：2 锁定
     */
    CLOSE_FOREVER("2");


    private final String code;
}
