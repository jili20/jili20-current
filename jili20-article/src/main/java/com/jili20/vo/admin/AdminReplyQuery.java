package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 后台管理 - 回复列表查询对象
 *
 * @author bing_  @create 2022/1/20-9:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminReplyQuery 对象", description = "后台管理 - 回复列表条件查询对象")
public class AdminReplyQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "留言ID")
    private Long commentId;

    @ApiModelProperty(value = "回复人ID")
    private Long fromUserId;

    @ApiModelProperty(value = "被回复人ID")
    private Long toUserId;

    @ApiModelProperty(value = "回复人名")
    private String fromUsername;

    @ApiModelProperty(value = "获赞总数")
    private Integer thumbCount;

    @ApiModelProperty(value = "查询开始时间", example = "2022-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-01-01 10:10:10")
    private String end;

}
