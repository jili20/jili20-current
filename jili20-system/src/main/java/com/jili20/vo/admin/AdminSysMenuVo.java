package com.jili20.vo.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 权限菜单表
 * </p>
 *
 * @author Bing
 * @since 2022-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminSysMenuVo 对象", description = "根据用户ID获取所拥有权限菜单表")
public class AdminSysMenuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 额外添加的属性
     * JsonInclude(JsonInclude.Include.NON_EMPTY) 有才封装，没有不封装
     */
    @ApiModelProperty(value = "子菜单集合")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private List<AdminSysMenuVo> children;


}
