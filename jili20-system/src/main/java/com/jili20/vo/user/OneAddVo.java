package com.jili20.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 投放诗语表（个人网站版）
 * </p>
 *
 * @author Bing
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneAddVo 对象", description = "投放诗语表（个人网站版）")
public class OneAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "赞助人头像")
    private String avatar;

    @ApiModelProperty(value = "赞助人昵称")
    private String username;

    @ApiModelProperty(value = "赞助致辞")
    private String message;

    @ApiModelProperty(value = "个人经历网址")
    private String link;

}
