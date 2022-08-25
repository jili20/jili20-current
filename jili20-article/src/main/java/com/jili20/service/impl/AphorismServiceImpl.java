package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jili20.entity.Aphorism;
import com.jili20.mapper.AphorismMapper;
import com.jili20.result.Result;
import com.jili20.service.AphorismService;
import com.jili20.vo.admin.AphorismAddVo;
import com.jili20.vo.admin.AphorismQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 随机诗语 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Slf4j
@Service
public class AphorismServiceImpl extends ServiceImpl<AphorismMapper, Aphorism> implements AphorismService {

    /**
     * 门户网站 - 首页 - 随机获取1条随机诗语
     *
     * @return
     */
    @Override
    public Result getRandomTip() {

        Aphorism randomTip = null;
        try {
            randomTip = baseMapper.getRandomTip();
        } catch (Exception e) {
            log.info("tip 赠言转化失败");
        }
        return Result.ok().data("randomTip", randomTip);
    }


    /***
     * 后台管理 - 随机诗语 - 条件分页查询随机诗语列表
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public IPage<Aphorism> search(Long current, Long size, AphorismQuery query) {
        LambdaQueryWrapper<Aphorism> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getAuthor())) {
            wrapper.like(Aphorism::getAuthor, query.getAuthor());
        }
        if (StringUtils.isNotBlank(query.getContent())) {
            wrapper.like(Aphorism::getContent, query.getContent());
        }
        wrapper.orderByDesc(Aphorism::getCreateTime);
        // 分页查询
        Page<Aphorism> pageParam = new Page<>(current, size);
        // 执行分页查询(只需要在 mapper 层传入封装好的分页组件即可，sql 分页条件组装的过程由mp自动完成)
        final Page<Aphorism> aphorismPage = baseMapper.selectPage(pageParam, wrapper);
        final List<Aphorism> records = aphorismPage.getRecords();
        if (records == null) {
            return null;
        }
        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 后台管理 - 随机诗语 - 新增
     *
     * @param vo
     * @return
     */
    @Override
    public Result add(AphorismAddVo vo) {
        final Aphorism aphorism = new Aphorism();
        aphorism.setAuthor(vo.getAuthor());
        aphorism.setContent(vo.getContent());
        aphorism.setCreateTime(LocalDateTime.now());
        final int count = baseMapper.insert(aphorism);
        if (count > 0) {
            return Result.ok().message("新增成功");
        }
        return Result.error().message("新增失败，请重试");
    }


    /**
     * 后台管理 - 随机诗语 - 根据ID编辑
     *
     * @param vo
     * @return
     */
    @Override
    public Result updateByAphorismId(AphorismAddVo vo) {
        final Aphorism aphorism = baseMapper.selectById(vo.getId());
        aphorism.setAuthor(vo.getAuthor());
        aphorism.setContent(vo.getContent());
        final int count = baseMapper.updateById(aphorism);
        if (count > 0) {
            return Result.ok().message("编辑成功");
        }
        return Result.error().message("编辑失败，请重试");
    }
}
