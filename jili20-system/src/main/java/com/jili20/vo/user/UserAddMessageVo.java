package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 门户网站-私信会话页面-新增私信对象
 * 这个接口收信人直接写用户名，需要根据用户名查出ID，存入数据库，跟之前的接口不一样
 * <p>
 * 私信表
 * </p>
 *
 * @author Bing
 * @since 2022-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserAddMessageVo 对象", description = "门户网站-私信会话页面-新增私信对象")
public class UserAddMessageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "私信目标用户名")
    private String username;

    @ApiModelProperty(value = "私信内容")
    private String content;

}
