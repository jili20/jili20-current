package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核未通过的原因 - 枚举
 *
 * @author bing_  @create 2022/4/17-11:55
 */
@Getter
@AllArgsConstructor
public enum AuditEnum {

    /**
     * 审核未通过的原因：0 无
     */
    NOTHING("0","无"),
    /**
     * 审核未通过的原因： 1 作品侵权
     */
    TORT("1","作品侵权"),
    /**
     * 审核未通过的原因： 2 涉嫌违法
     */
    ILLEGAL("2","涉嫌违法"),
    /**
     * 审核未通过的原因： 3 内容不雅
     */
    INDELICACY("3","内容不雅"),
    /**
     * 审核未通过的原因： 4 骚扰谩骂
     */
    HARASSMENT("4","骚扰谩骂"),
    /**
     * 审核未通过的原因： 5 虚假宣传
     */
    CHEAT("5","虚假宣传"),
    /**
     * 审核未通过的原因： 6 低质灌水
     */
    LOW("6","低质灌水"),
    /**
     * 审核未通过的原因： 7 不符合当前分类
     */
    MISMATCH("7","不符合当前分类");

    private final String code;
    private final String msg;

}
