package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bing_  @create 2022/4/17-12:32
 */
@Getter
@AllArgsConstructor
public enum TopicEnum {

    /**
     * 通知类型：0 留言
     */
    COMMENT("0"),
    /**
     * 通知类型：1 回复
     */
    REPLY("1"),
    /**
     * 通知类型: 2 点赞
     */
    THUMB("2"),
    /**
     * 通知类型: 3 关注
     */
    FOLLOW("3"),
    /**
     * 通知类型: 4 奖励
     */
    AWARD("4"),
    /**
     * 通知类型: 5 审核（轮播图审核通过或未通过、文章审核未通过...)
     */
    PUBLISH("5"),

    /**
     * 类型： 6 举报、投诉
     */
    REPORT("6");

    private final String code;
}
