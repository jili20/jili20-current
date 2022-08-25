package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bing_  @create 2022/4/17-12:20
 */
@Getter
@AllArgsConstructor
public enum ArticleEnum {

    /**
     * 文章状态：0 待审核
     */
    WAIT("0"),

    /**
     * 文章状态：1 审核通过
     */
    PASS("1"),

    /**
     * 文章状态：2 审核未通过
     */
    NO_PASS("2"),

    /**
     * 是否发布： 发布
     */
    PUBLISH_YES("1"),

    /**
     * 是否发布： 草稿
     */
    PUBLISH_NO("0"),

    /**
     * 是否推荐： 推荐
     */
    RECOMMEND_YES("1"),

    /**
     * 是否推荐： false
     */
    RECOMMEND_NO("0"),

    /**
     * 是否原创：原创
     */
    ORIGINAL_YES("0"),

    /**
     * 是否原创：转载
     */
    ORIGINAL_NO("1"),

    /**
     * 是否原创：翻译
     */
    ORIGINAL_TRANSLATION("3"),

    /**
     * 是否关闭留言功能：关闭
     */
    COMMENT_CLOSE_YES("1"),

    /**
     * 是否关闭留言功能：否
     */
    COMMENT_CLOSE_NO("0"),

    /**
     * 是否可转载： 转载请注明出处
     */
    REFUSE_YES("1"),

    /**
     * 是否可转载：未经作者授权，禁止转载
     */
    REFUSE_NO("0");

    private final String code;
}
