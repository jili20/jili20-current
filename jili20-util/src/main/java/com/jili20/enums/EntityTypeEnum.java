package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bing_  @create 2022/4/17-12:34
 */
@Getter
@AllArgsConstructor
public enum EntityTypeEnum {

    /**
     * 实体类型: 0 帖子
     */
    ARTICLE("0"),

    /**
     * 实体类型：1 留言
     */
    COMMENT("1"),

    /**
     * 实体类型：2 回复
     */
    REPLY("2"),

    /**
     * 实体类型： 3 用户
     */
    USER("3"),

    /**
     * 实体类型： 3 私信
     */
    LETTER("3"),

    /**
     * 实体类型： 4 轮播图审核通过
     */
    LOOPER_PASS("4"),

    /**
     * 实体类型： 5 轮播图片
     */
    LOOPER_NO_PASS("5"),

    /**
     * 类型： 6 文章设置为推荐
     */
    RECOMMEND("6"),

    /**
     * 类型： 7 文章审核未通过
     */
    ARTICLE_FAIL("7"),

    /**
     * 类型： 8 用户被禁言
     */
    FORBIDDEN_SPEAK("8");


    private final String code;
}
