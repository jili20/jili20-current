package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * VIP赞助人表 - 门户网站 - VIP赞助详情列表
 * </p>
 *
 * @author Bing
 * @since 2022-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SponsorListVo 对象", description = "VIP赞助详情列表")
public class UserPatronListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "赞助用户ID")
    private Long userId;

    @ApiModelProperty(value = "赞助金额")
    private Integer amount;

    @ApiModelProperty(value = "赞助致辞")
    private String message;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
