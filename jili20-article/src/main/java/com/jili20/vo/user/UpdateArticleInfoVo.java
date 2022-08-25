package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 前台编辑文章请求对象
 * @author bing_  @create 2022/1/20-17:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UpdateArticleInfoVo 对象", description = "前台编辑文章请求对象")
public class UpdateArticleInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "经历主人姓名")
    private String masterName;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "状态：(0 待审核；1 审核通过；2 审核未通过)")
    private String status;

    @ApiModelProperty(value = "是否发布：（0 false 草稿；1 true 发布）")
   private String publish;

    @ApiModelProperty(value = "文章分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "文章父分类ID")
    private Integer categoryPid;

    @ApiModelProperty(value = "经历主人头像URL")
    private String masterUrl;

    @ApiModelProperty(value = "是否原创：(0 原创；1 转载； 2 翻译)")
    private String original;

    @ApiModelProperty(value = "是否关闭留言功能：(0 开启；1 关闭)")
    private String commentClose;

    @ApiModelProperty(value = "可否转载：( 0 可转转；1 转载请注明出处；2 未经作者授权，禁止转载 )")
    private String refuse;

    @ApiModelProperty(value = "文章内容")
    private String content;

}

