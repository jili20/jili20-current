package com.jili20.vo.admin;

import com.jili20.entity.SysUser;
import com.jili20.util.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bing_  @create 2022/1/17-18:25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserQuery 对象", description = "用户查询条件")
public class SysUserQuery extends PageRequest<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "帐户状态:(0 正常, 1 禁言，2 锁定 )")
    private String status;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    /**
     * 注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
     */
    @ApiModelProperty(value = "查询开始时间", example = "2022-01-07 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-01-07 10:10:10")
    private String end;

}
