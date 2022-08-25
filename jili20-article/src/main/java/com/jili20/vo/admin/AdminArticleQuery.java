package com.jili20.vo.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 后台管理 - 文章列表查询对象
 *
 * @author bing_  @create 2022/1/20-9:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminArticleQuery 对象", description = "后台管理 - 文章列表条件查询对象")
public class AdminArticleQuery implements Serializable {

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

    @ApiModelProperty(value = "是否推荐：(0 正常；1 推荐)")
   private String recommend;

    @ApiModelProperty(value = "是否原创：(0 原创；1 转载； 2 翻译)")
    private String original;

    /**
     * 注意，这里使用 String 类型，前端传过来的数据无需进行类型转换
     */
    @ApiModelProperty(value = "查询开始时间", example = "2022-01-01 10:10:10")
    @TableField(exist = false)
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-01-01 10:10:10")
    @TableField(exist = false)
    private String end;

    @ApiModelProperty(value = "一级分类ID")
    private Long categoryPid;

}
