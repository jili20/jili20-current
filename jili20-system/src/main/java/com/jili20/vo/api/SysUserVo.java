package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Bing
 * @since 2022-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserVo 对象", description = "用户表")
public class SysUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "个性签名")
    private String sign;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户赞赏码")
    private String rewardUrl;

    @ApiModelProperty(value = "最后登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "注册日期")
    private LocalDateTime createTime;

}
