package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统通知表
 * </p>
 *
 * @author Bing
 * @since 2022-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserNoticeVo 对象", description = "系统通知分类通知列表数据对象")
public class UserNoticeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "通知的类型：( 0 留言；1、回复；2 点赞；3 关注；4 奖励；5 发布；6 举报 )")
    private String type;

    @ApiModelProperty(value = "通知内容（文章标题、留言内容、奖励等）")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "分类通知总数")
    private Integer typeCount;

    @ApiModelProperty(value = "分类通知未读总数")
    private Integer typeUnReadCount;

}
