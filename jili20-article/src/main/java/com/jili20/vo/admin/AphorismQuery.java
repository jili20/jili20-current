package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 随机诗语
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Aphorism 对象", description = "后台管理 - 随机诗语-条件查询对象")
public class AphorismQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "作者")
    private String author;

}
