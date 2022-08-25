package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 门户网站-编辑文章或草稿-获取文章详情
 *
 * @author bing_  @create 2022/1/20-14:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserEditArticleInfoVo 对象", description = "门户网站-编辑文章或草稿-获取文章详情")
public class UserEditArticleInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    
    private Long id;

    @ApiModelProperty(value = "作者ID")
    
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "经历主人姓名")
    private String masterName;

    @ApiModelProperty(value = "文章标题")
    private String title;

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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "文章内容")
    private String content;

}
