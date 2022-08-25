package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author bing_  @create 2022/1/18-15:49
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UpdateUserPasswordByAminVo 对象", description = "管理员重置用户密码请求对象")
public class UpdateUserPasswordByAminVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long userId;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "确认密码", required = true)
    private String confirmPassword;

}
