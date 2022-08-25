package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 奖励表
 * </p>
 *
 * @author Bing
 * @since 2022-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserAwardListVo 对象", description = "门户网站-个人主页-用户获奖励列表请求对象")
public class UserAwardListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "当前用户ID")
    private Long userId;

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "奖励的实体类型：（1 帖子；2 留言；3 用户）")
    private String entityType;

    @ApiModelProperty(value = "被奖励的实体的作者ID")
    private Long entityUserId;

    @ApiModelProperty(value = "赞助金额")
    private String amount;

    @ApiModelProperty(value = "赞助时间")
    private LocalDateTime createTime;

    /**
     * 以下字段去查实时数据
     */
    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "当前用户名")
    private String username;

    @ApiModelProperty(value = "当前用户头像")
    private String avatar;

}
