package com.jili20.vo.admin;

import com.jili20.entity.SysRole;
import com.jili20.util.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Admin - 角色管理 - 角色列表查询条件对象
 * @author bing_  @create 2022/1/15-22:42
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysRoleQuery 对象", description = "角色列表查询条件")
public class SysRoleQuery extends PageRequest<SysRole> {

    @ApiModelProperty(value = "角色名称")
    private String name;
}

