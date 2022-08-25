package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 统计用户所有项
 * @author bing_  @create 2022/2/27-14:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "StatisticVo 对象", description = "门户网站个人主页统计用户所有项请求对象")
public class StatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户文章总数")
    private Integer articleCount;

    @ApiModelProperty(value = "用户留言回复总数")
    private Integer commentCount;

    @ApiModelProperty(value = "用户轮播图片总数")
    private Integer looperCount;


}
