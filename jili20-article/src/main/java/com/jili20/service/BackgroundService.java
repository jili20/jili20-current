package com.jili20.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.entity.Background;
import com.jili20.result.Result;
import com.jili20.vo.admin.BackgroundAddVo;
import com.jili20.vo.admin.BackgroundQuery;

/**
 * <p>
 * 轮播图背景图表 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-04-02
 */
public interface BackgroundService extends IService<Background> {

    /**
     * 获取一条数据
     * @param wrapper
     * @return
     */
    default Background getOnly(QueryWrapper<Background> wrapper) {
        wrapper.last("limit 1");
        return this.getOne(wrapper);
    }

    /**
     * 后台管理 - 轮播图背景图管理 - 新增首页轮播图背景图片
     *
     * @param vo
     * @return
     */
    Result add(BackgroundAddVo vo);


    /**
     * 后台管理 - 轮播图背景图管理 - 条件分页查询轮播图背景图列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    IPage<Background> selectPage(Long current, Long size, BackgroundQuery query);


    /**
     * 后台管理 - 轮播图背景图管理 - 设置为启用 或 失效
     *
     * @param id
     * @param status
     * @return
     */
    Result updateStatus(Long id, String status);


    /***
     * 后台管理 - 轮播图背景图管理 - 根据ID删除背景图片
     * @param id
     * @return
     */
    Result removeByBackgroundId(Long id);


    /**
     * 门户网站 - 获取最新一张启用的轮播图背景图片
     * @return
     */
    Result getOnlyOne();

}
