package com.jili20.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.entity.One;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.result.Result;
import com.jili20.vo.api.OneQueryVo;
import com.jili20.vo.api.OneTopVo;
import com.jili20.vo.user.OneAddVo;

import java.util.List;

/**
 * <p>
 * 投放诗语表（个人网站版） 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-04-29
 */
public interface OneService extends IService<One> {

    /**
     * 门户网站 - 首页 - 新增 - 投放诗语
     * @param vo
     * @return
     */
    Result add(OneAddVo vo);


    /**
     * 门户网站 - 首页 - 查询前36位1元赞助人
     * @return
     */
    List<OneTopVo> getOneTop();


    /**
     * 门户网站 - 条件分页查询 - 所有投放诗语列表
     * @param current
     * @param size
     * @param query
     * @return
     */
    IPage<One> listPage(Long current, Long size, OneQueryVo query);


    /**
     * 门户网站 - 通知详情页面 - 根据ID删除通知
     * @param id
     * @return
     */
    Result removeByOneId(Long id);
}