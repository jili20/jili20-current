package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Admin - 权限菜单 - 列表查询条件对象
 * @author bing_  @create 2022/1/15-22:42
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMenuQuery 对象", description = "菜单列表查询条件")
public class SysMenuQuery implements Serializable {

    @ApiModelProperty(value = "菜单名称")
    private String name;
}

