package com.jili20.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jili20.aliyun.AliyunUtil;
import com.jili20.aliyun.Properties;
import com.jili20.entity.Article;
import com.jili20.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 多线程
 *
 * @author bing_  @create 2022/1/13-15:44
 */
@Slf4j
@Component
public class ThreadService {

    @Resource
    private Properties properties;

    /**
     * 更新文章浏览数
     * 门户网站 -文章详情 - 根据文章id查询文章详情 - 使用多线程更新文章浏览数
     *
     * @param articleMapper
     * @param articleId
     */
    public void updateViewCount(ArticleMapper articleMapper, Long articleId) {
        Article article = articleMapper.selectById(articleId);

        // 设置多一个文章对象，为了在多线程环境下，线程安全
        Article newArticle = new Article();
        newArticle.setViewCount(article.getViewCount() + 1);

        articleMapper.update(newArticle, new QueryWrapper<Article>()
                .eq("id", article.getId())
                .eq("view_count", article.getViewCount()));
    }



    /**
     * 门户网站 - 文章详情 - 根据ID逻辑删除文章 - 删除文章内容 - 删除阿里云OSS中文章图片
     *
     * @param content
     */
    public void deletePictureList(String content, Long articleId) {
        // 正则匹配 url
        String regStr = "((http|https)://)([\\w-]+\\.)+[\\w-]+/(article)/\\d{4}/\\d{2}/\\d{2}/\\w+\\.(jpeg|png|gif|jpg)";
        Pattern pattern = Pattern.compile(regStr);
        // 3、创建匹配器（说明：创建匹配器 matcher，按照 正则表达式的规则，去匹配 content 字符串）
        Matcher matcher = pattern.matcher(content);
        if (ObjectUtils.allNotNull(matcher)) {
            // 4、开始匹配
            while (matcher.find()) {
                // 循环删除阿里云图片
                try {
                    AliyunUtil.delete(matcher.group(0), properties.getAliyun());
                } catch (Exception e) {
                    log.info("删除阿里云OSS图片异常");
                    e.printStackTrace();
                }
            }
        }
    }
}
