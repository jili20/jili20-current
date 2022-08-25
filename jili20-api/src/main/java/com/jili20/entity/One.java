package com.jili20.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 投放诗语表（个人网站版）
 * </p>
 *
 * @author Bing
 * @since 2022-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "One对象", description = "投放诗语表（个人网站版）")
public class One implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String username;

    @ApiModelProperty(value = "内容")
    private String message;

    @ApiModelProperty(value = "个人经历网址")
    private String link;

    @ApiModelProperty(value = "时间")
    private LocalDateTime createTime;


}
