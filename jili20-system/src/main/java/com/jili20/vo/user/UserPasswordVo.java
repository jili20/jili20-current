package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author bing_  @create 2022/3/5-16:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserPasswordVo 对象", description = "门户网站私人空间用户修改密码请求对象")
public class UserPasswordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "旧密码",required = true)
    private String password;

    @ApiModelProperty(value = "新密码",required = true)
    private String newPassword;

    @ApiModelProperty(value = "确认密码", required = true)
    private String confirmPassword;

}
