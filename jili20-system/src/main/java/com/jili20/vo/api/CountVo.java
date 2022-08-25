package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 统计用户-投放诗语、VIP赞助、关注、粉丝总数
 * @author bing_  @create 2022/2/27-14:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CountVo 对象", description = "门户网站个人主页统计用户投放诗语、VIP赞助、关注、粉丝总数请求对象")
public class CountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户关注总数")
    private Integer followCount;

    @ApiModelProperty(value = "用户粉丝总数")
    private Integer followerCount;

    @ApiModelProperty(value = "用户1元赞助总数")
    private Integer patronCount;

    @ApiModelProperty(value = "用户VIP赞助总数")
    private Integer sponsorCount;

}
