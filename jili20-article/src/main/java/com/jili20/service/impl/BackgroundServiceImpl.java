package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jili20.aliyun.AliyunUtil;
import com.jili20.aliyun.Properties;
import com.jili20.entity.Background;
import com.jili20.mapper.BackgroundMapper;
import com.jili20.result.Result;
import com.jili20.service.BackgroundService;
import com.jili20.util.AuthUtil;
import com.jili20.util.RedisCache;
import com.jili20.vo.admin.BackgroundAddVo;
import com.jili20.vo.admin.BackgroundQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 轮播图背景图表 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-04-02
 */
@Service
public class BackgroundServiceImpl extends ServiceImpl<BackgroundMapper, Background> implements BackgroundService {

    @Resource
    private Properties properties;

    @Resource
    private BackgroundService backgroundService;

    @Resource
    private RedisCache redisCache;

    /**
     * 后台管理 - 轮播图背景图管理 - 新增首页轮播图背景图片
     *
     * @param vo
     * @return
     */
    @Override
    public Result add(BackgroundAddVo vo) {
        final Background background = new Background();
        background.setUserId(AuthUtil.getAuthUserId());
        background.setUrl(vo.getUrl());
        background.setStatus(Background.STATUS_WAIT);
        background.setCreateTime(LocalDateTime.now());
        // 插入数据库
        final int count = baseMapper.insert(background);
        if (count > 0) {
            return Result.ok().message("新增背景图片成功");
        }
        return Result.error().message("新增背景图片失败，请重试");
    }


    /**
     * 后台管理 - 轮播图背景图管理 - 条件分页查询轮播图背景图列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public IPage<Background> selectPage(Long current, Long size, BackgroundQuery query) {
        // 组装查询条件
        LambdaQueryWrapper<Background> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(query.getUserId()) && query.getUserId() > 0, Background::getUserId, query.getUserId());
        wrapper.eq(Objects.nonNull(query.getStatus()), Background::getStatus, query.getStatus());
        wrapper.ge(Objects.nonNull(query.getBegin()), Background::getCreateTime, query.getBegin());
        wrapper.le(Objects.nonNull(query.getEnd()), Background::getCreateTime, query.getEnd());
        wrapper.orderByAsc(Background::getStatus);
        wrapper.orderByDesc(Background::getUpdateTime);
        // 分页查询
        final Page<Background> pageParam = new Page<>(current, size);
        final Page<Background> backgroundPage = baseMapper.selectPage(pageParam, wrapper);
        final List<Background> records = backgroundPage.getRecords();
        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 后台管理 - 轮播图背景图管理 - 设置为启用 或 失效
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Result updateStatus(Long id, String status) {
        // 删除 redis 里的数据
        redisCache.deleteObject("background:");
        // 先查后改
        final Background background = baseMapper.selectById(id);
        background.setStatus(status);
        background.setUpdateTime(LocalDateTime.now());
        final int count = baseMapper.updateById(background);
        if (count > 0) {
            return Result.ok().message("修改状态成功");
        }
        return Result.error().message("修改状态失败，请重试");
    }


    /**
     * 后台管理 - 轮播图背景图管理 - 根据ID删除背景图片
     *
     * @param id
     * @return
     */
    @Override
    public Result removeByBackgroundId(Long id) {
        // 删除 redis 里的数据
        redisCache.deleteObject("background:");
        // 先查，确定有再删除
        final Background background = baseMapper.selectById(id);
        if (background == null) {
            return Result.error().message("背景图不存在");
        }
        // 删除阿里云OSS里的图片
        AliyunUtil.delete(background.getUrl(), properties.getAliyun());
        // 删除轮播图表数据
        final int count = baseMapper.deleteById(id);
        if (count > 0) {
            return Result.ok().message("删除背景图片成功");
        }
        return Result.error().message("删除背景图片失败，请重试");
    }


    /**
     * 门户网站 - 首页 / 搜索页面 - 获取最新一张启用的轮播图背景图片
     * 将图片地址 存入 redis 1 小时
     *
     * @return
     */
    @Override
    public Result getOnlyOne() {
        // 先从 redis 里取
        String redisCode = redisCache.getCacheObject("background:");
        if (StringUtils.isNotBlank(redisCode)){
            return Result.ok().data("url", redisCode);
        }
        // redis 里没有，再查询数据库
        QueryWrapper<Background> wrapper = new QueryWrapper<>();
        wrapper.eq("status", Background.STATUS_WORKING)
                .orderByDesc("update_time");
        final String url = backgroundService.getOnly(wrapper).getUrl();
        // 存进redis - 有效时长 1 小时
        redisCache.setCacheObject("background:", url, 1, TimeUnit.HOURS);
        return Result.ok().data("url", url);
    }


}
