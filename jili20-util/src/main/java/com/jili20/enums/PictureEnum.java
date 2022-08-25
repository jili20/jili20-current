package com.jili20.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所有文章图片 - 类型 - 枚举
 *
 * @author bing_  @create 2022/4/17-11:40
 */
@Getter
@AllArgsConstructor
public enum PictureEnum {

    /**
     * 所有文章图片枚举: 0 所有图片
     */
    ALL("0"),
    /**
     * 所有文章图片枚举: 1 在用图片
     */
    USE("1"),
    /**
     * 所有文章图片枚举: 2 已删除图片
     */
    DEL("2");

    private final String code;

}
