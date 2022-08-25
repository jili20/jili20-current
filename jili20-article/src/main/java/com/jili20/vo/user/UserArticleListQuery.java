package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 门户网站 - 用户文章列表 - 条件查询对象
 *
 * @author bing_  @create 2022/1/20-9:37
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserArticleListQuery 对象", description = "用户文章列表查询条件")
public class UserArticleListQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态：(0 待审核；1 审核通过；2 审核未通过)")
    private String status;

    @ApiModelProperty(value = "是否发布：（0 false 草稿；1 true 发布）")
   private String publish;

    @ApiModelProperty(value = "文章分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "文章父分类ID")
    private Integer categoryPid;

    @ApiModelProperty(value = "留言总数")
    private Integer commentCount;

    @ApiModelProperty(value = "点赞总数")
    private Integer thumbCount;

    @ApiModelProperty(value = "浏览总数")
    private Integer viewCount;

    @ApiModelProperty(value = "收藏总数")
    private Integer collectionCount;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

}
