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
@ApiModel(value = "UseAvatarVo 对象", description = "门户网站私人空间用户修改头像请求对象")
public class UseAvatarVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户头像")
    private String avatar;
}
