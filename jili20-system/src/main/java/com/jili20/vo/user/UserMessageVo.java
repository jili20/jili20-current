package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 私信表
 * </p>
 *
 * @author Bing
 * @since 2022-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserMessageVo 对象", description = "门户网站-私信通知主页-私信请求对象")
public class UserMessageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "会话ID")
    private String conversationId;

    @ApiModelProperty(value = "私信内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "会话私信总数")
    private Integer conversationMessageCount;

    @ApiModelProperty(value = "会话未读私信总数")
    private Integer unreadConversationMessageCount;

    @ApiModelProperty(value = "私信目标用户ID")
    private Long userId;

    @ApiModelProperty(value = "私信目标用户名")
    private String username;

    @ApiModelProperty(value = "私信目标用户头像")
    private String avatar;


}
