package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author bing_  @create 2022/2/7-20:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ArticleRecommendVo 对象", description = "推荐的文章请求对象")
public class ArticleRecommendVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

}
