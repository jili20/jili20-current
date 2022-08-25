package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 门户网站-私人空间-草稿列表请求对象
 *
 * @author bing_  @create 2022/1/20-9:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserDraftArticleListVo 对象", description = "门户网站-私人空间-草稿列表请求对象")
public class UserDraftArticleListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "作者ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "状态：(0 待审核；1 审核通过；2 审核未通过)")
    private String status;

    @ApiModelProperty(value = "审核不通过的原因：(0 无；1 作品侵权；2 涉嫌违法犯法；3 色情不雅；4 骚扰谩骂；5 虚假宣传；6 低质灌水；7 不符合当前分类 )")
    private String fail;

    @ApiModelProperty(value = "是否发布：（0 false 草稿；1 true 发布）")
   private String publish;

    @ApiModelProperty(value = "文章分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "文章父分类ID")
    private Integer categoryPid;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "父分类名称")
    private String categoryParentName;

}
