package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 轮播图背景图表
 * </p>
 *
 * @author Bing
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BackgroundQuery 对象", description = "轮播图背景图条件查询对象")
public class BackgroundQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "状态（ 0 待用；1 启用；2 停用 ）")
    private String status;

    @ApiModelProperty(value = "查询开始时间", example = "2022-03-26 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-03-26 10:10:10")
    private String end;

}
