package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举
 *
 * @author bing_  @create 2022/4/17-12:12
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    /**
     * 状态： 正常
     */
    NORMAL("0"),
    /**
     * 状态：停用
     */
    STOP("1"),
    /**
     * 状态：0 等待审核
     */
    WAIT("0"),

    /**
     * 状态：1 审核通过
     */
    PASS("1"),

    /**
     * 状态：2 审核未通过
     */
    NO_PASS("2"),

    /**
     * 状态：3 无
     */
    NOTHING("3");


    private final String code;

}
