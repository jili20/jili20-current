package com.jili20.vo.admin;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author Bing
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Announcement 对象", description = "公告表")
public class AnnouncementUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String content;

}
