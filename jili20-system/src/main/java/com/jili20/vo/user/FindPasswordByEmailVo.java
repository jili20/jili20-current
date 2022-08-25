package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户 - 邮箱验证码 - 找回密码 - 请求对象
 * @author bing_  @create 2022/2/1-21:46
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FindPasswordByEmailVo 对象", description = "用户邮箱验证码找回密码请求对象")
public class FindPasswordByEmailVo implements Serializable {

    @ApiModelProperty(value = "邮箱地址",required = true)
    private String email;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    @ApiModelProperty(value = "密码",required = true)
    private String newPassword;

    @ApiModelProperty(value = "确认密码", required = true)
    private String confirmPassword;
}
