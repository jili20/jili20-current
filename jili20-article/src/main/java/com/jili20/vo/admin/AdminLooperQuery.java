package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 轮播图表
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminLooperQuery 对象", description = "后台管理 - 轮播图片 - 条件搜索对象")
public class AdminLooperQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "状态：（ 0:待审核; 1 审核通过播放中; 2 审核未通过; 3 留存）")
    private String status;

    @ApiModelProperty(value = "发布者ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "图片标题")
    private String title;

    @ApiModelProperty(value = "跳转的网址")
    private String looperLink;

    @ApiModelProperty(value = "位置：(0 首页顶部；1 首页右则；2 帖子正文下)")
    private String position;

    @ApiModelProperty(value = "查询开始时间", example = "2022-03-26 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-03-26 10:10:10")
    private String end;

}
