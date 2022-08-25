package com.jili20.mapper;

import com.jili20.entity.Aphorism;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 随机诗语 Mapper 接口
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
public interface AphorismMapper extends BaseMapper<Aphorism> {


    /**
     * 门户网站 - 首页 - 随机获取1条随机诗语
     * @return
     */
    Aphorism getRandomTip();
}
