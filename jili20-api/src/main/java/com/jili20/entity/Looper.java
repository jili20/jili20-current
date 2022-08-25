package com.jili20.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 轮播图表
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Looper对象", description = "轮播图表")
public class Looper implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "发布者ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "图片标题")
    private String title;

    @ApiModelProperty(value = "状态：（ 0 待审核; 1 审核未通过；2 审核通过 ）")
    private String status;

    @ApiModelProperty(value = "图片")
    @TableField("looper_url")
    private String looperUrl;

    @ApiModelProperty(value = "跳转的网址")
    @TableField("looper_link")
    private String looperLink;

    @ApiModelProperty(value = "位置：(0 首页轮播图；1 首页右则；2 帖子正文下)")
    private String position;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
