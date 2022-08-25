package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 管理员 - 新增用户 - 请求对象
 * @author bing_  @create 2022/1/17-23:45
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AddUserVo 对象", description = "管理员新增用户请求对象")
public class AddUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

}

