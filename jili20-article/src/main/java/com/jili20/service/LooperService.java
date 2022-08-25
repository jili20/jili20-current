package com.jili20.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.entity.Looper;
import com.jili20.result.Result;
import com.jili20.vo.admin.AdminLooperQuery;
import com.jili20.vo.api.LooperQuery;
import com.jili20.vo.api.LooperTop15Vo;
import com.jili20.vo.api.LooperVo;
import com.jili20.vo.user.LooperIndexVo;
import com.jili20.vo.user.UserLooperEditVo;

import java.util.List;

/**
 * <p>
 * 轮播图表 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
public interface LooperService extends IService<Looper> {

    /**
     * 门户网站 - 用户 - 新增轮播图片
     *
     * @param vo
     * @return
     */
    Result add(LooperIndexVo vo);


    /**
     * 门户网站 - 用户 - 编辑轮播图片
     *
     * @param vo
     * @return
     */
    Result updateByLooperId(UserLooperEditVo vo);


    /**
     * 门户网站 - 轮播图 - 审核通过的 - 首页顶部
     *
     * @return
     */
    List<LooperVo> looperList();


    /**
     * 门户网站 - 轮播图 - 审核通过的 - 首页右则
     *
     * @return
     */
    List<LooperVo> looperListIndexRight();


    /**
     * 门户网站 - 轮播图 - 审核通过的 - 文章详情中
     *
     * @return
     */
    List<LooperVo> looperListArticle();


    /**
     * 门户网站 - 轮播图 - 审核通过的 - 赞助详情右则
     *
     * @return
     */
    List<LooperVo> looperListPatronRight();


    /**
     * 门户网站 - 新增轮播图页面 - 获取前15条轮播图信息
     *
     * @return
     */
    List<LooperTop15Vo> looperTop15List();


    /**
     * 门户网站 - 条件分页查询 - 已审核通过的所有轮播图列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Result search(Long current, Long size, LooperQuery query);


    /**
     * 门户网站 - 个人主页 - 用户投图赞助列表
     *
     * @param userId
     * @param current
     * @param size
     * @return
     */
    Result getUserLooperListPage(Long userId, Long current, Long size);


    /**
     * 门户网站 - 私人空间 - 用户投放的所有轮播图
     *
     * @param userId
     * @param current
     * @param size
     * @return
     */
    Result getUserAllLooperListPage(Long userId, Long current, Long size);


    /**
     * 门户网站 - 私人空间 - 根据ID删除轮播图
     *
     * @param id
     * @return
     */
    Result removeByLooperId(Long id);


    /**
     * 后台管理 - 投图赞助管理 - 条件分页查询投图赞助列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    IPage<Looper> selectPage(Long current, Long size, AdminLooperQuery query);


    /**
     * 后台管理 - 投图赞助管理 - 图片审核：通过 / 不通过
     *
     * @param id      图片ID
     * @param status  状态
     * @return
     */
    Result updateStatus(Long id, String status);


    /**
     * 后台管理 - 首页 - 统计投图赞助总记录数
     * @return
     */
    Result getTotal();
}
