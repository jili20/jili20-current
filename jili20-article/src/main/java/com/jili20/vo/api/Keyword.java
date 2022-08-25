package com.jili20.vo.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 门户网站-ES文章搜索对象
 *
 * @author bing_  @create 2022/1/20-9:38
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "keyword 对象", description = "搜索对象")
public class Keyword implements Serializable {

    @ApiModelProperty(value = "作者头像")
    private String keyword;

}
