package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bing_  @create 2022/4/18-12:49
 */
@Getter
@AllArgsConstructor
public enum AnnEnum {

    /**
     * 公告状态：0 启用
     */
    USE("0"),

    /**
     * 公告状态：1 待用；
     */
    WAIT("1"),

    /**
     * 公告状态：2 停用；
     */
    NO_USER("2");

    private final String code;
}
