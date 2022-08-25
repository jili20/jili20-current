package com.jili20.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 轮播图背景图表
 * </p>
 *
 * @author Bing
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Background对象", description = "轮播图背景图表")
public class Background implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 背景图片状态： 0 启用
     */
    public static final String STATUS_WORKING = "0";

    /**
     * 背景图片状态： 1 待用
     */
    public static final String STATUS_WAIT = "1";

    /**
     * 背景图片状态： 2 停用
     */
    public static final String STATUS_STOP = "2";


    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "状态（ 0 待用；1 启用；2 停用 ）")
    private String status;

    @ApiModelProperty(value = "背景图片地址")
    private String url;

    @ApiModelProperty(value = "是否删除：( 0 正常；1 已删除 )")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
