package com.jili20.vo.api;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章分类表
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Category对象", description = "文章分类表")
public class CategoryNameVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类名称")
    @TableField("category_name")
    private String categoryName;

    @ApiModelProperty(value = "父分类名称")
    private String categoryParentName;

}
