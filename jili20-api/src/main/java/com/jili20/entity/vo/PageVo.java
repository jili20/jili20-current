package com.jili20.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author bing_  @create 2022/2/8-8:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    private List rows;

    private Long total;
}
