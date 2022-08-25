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
@ApiModel(value = "UserPhoneVo 对象", description = "门户网站私人空间用户修改手机号码请求对象")
public class UserPhoneVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号码",required = true)
    private String phone;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

}
