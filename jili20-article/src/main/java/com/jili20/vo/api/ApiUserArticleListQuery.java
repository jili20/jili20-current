package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 门户网站-个人主页-文章列表查询条件
 *
 * @author bing_  @create 2022/1/20-9:37
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ApiUserArticleListQuery 对象", description = "门户网站-个人主页-文章列表查询条件")
public class ApiUserArticleListQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "留言总数")
    private Integer commentCount;

    @ApiModelProperty(value = "浏览总数")
    private Integer viewCount;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

}
