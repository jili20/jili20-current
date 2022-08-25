package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 前台首页文章列表查询对象
 *
 * @author bing_  @create 2022/1/20-9:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ArticleIndexListVo 对象", description = "前台首页文章列表查询对象")
public class ArticleIndexListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "作者ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "经历主人头像URL")
    private String masterUrl;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "文章父分类ID")
    private Integer categoryPid;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "父分类名称")
    private String categoryParentName;

    @ApiModelProperty(value = "作者头像URL")
    private String avatar;

    @ApiModelProperty(value = "留言总数")
    private Integer commentCount;

    @ApiModelProperty(value = "浏览总数")
    private Integer viewCount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
