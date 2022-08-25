package com.jili20.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Data
@Accessors(chain = true)

@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Article对象", description = "文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "作者ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "经历主人姓名")
    @TableField("master_name")
    private String masterName;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "是否发布：（0 false 草稿；1 true 发布）")
    private String publish;

    @ApiModelProperty(value = "文章分类ID")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty(value = "文章父分类ID")
    @TableField("category_pid")
    private Integer categoryPid;

    @ApiModelProperty(value = "作者头像URL")
    private String avatar;

    @ApiModelProperty(value = "经历主人头像URL")
    @TableField("master_url")
    private String masterUrl;

    @ApiModelProperty(value = "是否推荐：(0 正常；1 推荐)")
    private String recommend;

    @ApiModelProperty(value = "浏览总数")
    @TableField("view_count")
    private Integer viewCount;

    @ApiModelProperty(value = "逻辑删除：(0 正常；1 已删除)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
