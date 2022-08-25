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
@ApiModel(value = "BackgroundAddVo 对象", description = "新增轮播图对象")
public class BackgroundAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "背景图片地址")
    private String url;

}
