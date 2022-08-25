package com.jili20.vo.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 文章分类表
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CategoryVo 对象", description = "文章分类请求表")
public class CategoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "父分类ID")
    private Integer parentId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 封装当前分类 - 所有子分类
     * JsonInclude(JsonInclude.Include.NON_EMPTY) 有子分类才封装，没有不封装
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryVo> children;
}
