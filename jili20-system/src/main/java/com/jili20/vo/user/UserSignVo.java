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
@ApiModel(value = "UserSignVo 对象", description = "门户网站私人空间用户修改个性签名请求对象")
public class UserSignVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户个性签名")
    private String sign;
}
