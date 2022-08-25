package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bing_  @create 2022/4/17-12:39
 */
@Getter
@AllArgsConstructor
public enum MenuEnum {

    /**
     * 菜单类型 1 目录
     */
    DIR("1"),

    /**
     * 菜单类型 2 菜单
     */
    MENU("2"),

    /**
     * 菜单类型 3 按钮
     */
    BTN("3");

    private final String code;
}
