package com.jili20.vo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author Bing
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Announcement 对象", description = "条件查询公告请求对象")
public class AnnouncementQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "发布者ID")
    private Long userId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态：(0 待用；1 展示中；2 已过期；)")
    private String status;

    @ApiModelProperty(value = "查询开始时间", example = "2022-01-01 10:10:10")
    @TableField(exist = false)
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-01-01 10:10:10")
    @TableField(exist = false)
    private String end;

}
