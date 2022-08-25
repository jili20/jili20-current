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
@ApiModel(value = "UserEmailVo 对象", description = "门户网站私人空间用户修改邮箱请求对象")
public class UserEmailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮箱地址",required = true)
    private String email;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

}
