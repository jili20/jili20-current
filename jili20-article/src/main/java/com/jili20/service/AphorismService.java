package com.jili20.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.entity.Aphorism;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.result.Result;
import com.jili20.vo.admin.AphorismAddVo;
import com.jili20.vo.admin.AphorismQuery;

/**
 * <p>
 * 随机诗语 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
public interface AphorismService extends IService<Aphorism> {


    /**
     * 门户网站 - 首页 - 随机获取1条随机诗语
     * @return
     */
    Result getRandomTip();


    /**
     * 后台管理 - 随机诗语 - 条件分页查询随机诗语列表
     * @param current
     * @param size
     * @param query
     * @return
     */
    IPage<Aphorism> search(Long current, Long size, AphorismQuery query);


    /**
     * 后台管理 - 随机诗语 - 新增
     * @param vo
     * @return
     */
    Result add(AphorismAddVo vo);


    /**
     * 后台管理 - 随机诗语 - 根据ID编辑
     * @param vo
     * @return
     */
    Result updateByAphorismId(AphorismAddVo vo);
}
