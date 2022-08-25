package com.jili20.vo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 所有文章图片表
 * </p>
 *
 * @author Bing
 * @since 2022-04-09
 */
@Data
@TableName("all_picture")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PictureAddVo 对象", description = "新增所有文章图片表")
public class PictureAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "图片地址")
    private String url;

}
