package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bing_  @create 2022/4/17-12:29
 */
@Getter
@AllArgsConstructor
public enum LoopPositionEnum {

    /**
     * 轮播图位置："0" 首页顶部
     */
    INDEX_TOP("0"),
    /**
     * 轮播图位置："1" 首页右则
     */
    INDEX_RIGHT("1"),
    /**
     * 轮播图位置："2" 文章详情下
     */
    AT_ARTICLE("2");

    private final String code;
}
