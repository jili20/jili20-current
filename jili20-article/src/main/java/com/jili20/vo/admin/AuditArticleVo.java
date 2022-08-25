package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author bing_  @create 2022/3/25-18:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AuditArticleVo 对象", description = "后台管理 - 审核文章对象")
public class AuditArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "作者ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "经历主人姓名")
    private String masterName;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "状态：(0 待审核；1 审核通过；2 审核未通过)")
    private String status;

    @ApiModelProperty(value = "是否发布：（0 false 草稿；1 true 发布）")
   private String publish;

    @ApiModelProperty(value = "作者头像URL")
    private String avatar;

    @ApiModelProperty(value = "经历主人头像URL")
    private String masterUrl;

    @ApiModelProperty(value = "是否原创：(0 原创；1 转载； 2 翻译)")
    private String original;

    @ApiModelProperty(value = "是否关闭留言功能：(0 开启；1 关闭)")
    private String commentClose;

    @ApiModelProperty(value = "是否可转载：( 0 未经作者授权，禁止转载；1 转载请注明出处；)")
    private String refuse;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "父分类名称")
    private String categoryParentName;

    @ApiModelProperty(value = "文章内容")
    private String content;

}
