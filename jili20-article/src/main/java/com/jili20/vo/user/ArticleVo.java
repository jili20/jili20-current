package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户新增文章 - 需要提交的属性
 * @author bing_  @create 2022/1/19-14:54
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ArticleVo 对象", description = "用户新增文章请求对象")
public class ArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "经历主人姓名")
    private String masterName;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "是否发布：（0 草稿；1 发布）")
   private String publish;

    @ApiModelProperty(value = "文章分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "文章父分类ID")
    private Integer categoryPid;

    @ApiModelProperty(value = "经历主人头像URL")
    private String masterUrl;

    /**
     * 添加文章内容属性
     */
    @ApiModelProperty(value = "文章内容")
    private String content;

}
