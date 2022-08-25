package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author Bing
 * @since 2022-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserThumbListVo 对象", description = "门户网站-个人主页-用户收到的赞")
public class UserThumbListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "点赞人ID")
    private Long userId;

    @ApiModelProperty(value = "点赞人名")
    private String username;

    @ApiModelProperty(value = "点赞人头像")
    private String avatar;

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "点赞的目标ID（文章ID 或 留言ID）")
    private Long entityId;

    @ApiModelProperty(value = "点赞的内容：（0 帖子标签；1 留言 内容）")
    private String content;

    @ApiModelProperty(value = "点赞的实体类型：（0 帖子；1 留言）")
    private String entityType;

    @ApiModelProperty(value = "被点赞的实体的用户ID")
    private Long entityUserId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
