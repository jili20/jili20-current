package com.jili20.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jili20.entity.Article;
import com.jili20.entity.Looper;
import com.jili20.enums.ArticleEnum;
import com.jili20.mapper.ArticleMapper;
import com.jili20.mapper.LooperMapper;
import com.jili20.result.Result;
import com.jili20.vo.api.StatisticVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author bing_  @create 2022/2/27-14:37
 */
@Service
public class StatisticService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private LooperMapper looperMapper;


    /**
     * 门户网站 - 个人主页 - 统计用户各项总数（文章微服务）
     *
     * @param userId
     * @return
     */
    public Result total(Long userId) {
        // 用户文章总数
        final Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getUserId, userId)
                .eq(Article::getPublish, ArticleEnum.PUBLISH_YES.getCode()));
        // 用户轮播图片总数
        final Integer looperCount = looperMapper.selectCount(new LambdaQueryWrapper<Looper>()
                .eq(Looper::getUserId, userId));
        // 把各项统计封装到 Vo里
        final StatisticVo vo = new StatisticVo();
        vo.setArticleCount(articleCount);
        vo.setLooperCount(looperCount);
        return Result.ok().data("statistic", vo);
    }

}
