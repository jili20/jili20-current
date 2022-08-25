package com.jili20.vo.user;

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
@ApiModel(value = "Looper对象", description = "轮播图表")
public class UserLooperEditVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "图片标题")
    private String title;

    @ApiModelProperty(value = "图片")
    private String looperUrl;

    @ApiModelProperty(value = "跳转的网址")
    private String looperLink;

    @ApiModelProperty(value = "位置：(0 首页轮播图；1 首页右则；2 帖子正文下)")
    private String position;

}
