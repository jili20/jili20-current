package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jili20.aliyun.AliyunUtil;
import com.jili20.aliyun.Properties;
import com.jili20.entity.Looper;
import com.jili20.enums.ArticleEnum;
import com.jili20.enums.LoopPositionEnum;
import com.jili20.enums.StatusEnum;
import com.jili20.mapper.LooperMapper;
import com.jili20.result.Result;
import com.jili20.service.LooperService;
import com.jili20.util.AuthUtil;
import com.jili20.vo.admin.AdminLooperQuery;
import com.jili20.vo.api.LooperListVo;
import com.jili20.vo.api.LooperQuery;
import com.jili20.vo.api.LooperTop15Vo;
import com.jili20.vo.api.LooperVo;
import com.jili20.vo.user.LooperIndexVo;
import com.jili20.vo.user.UserAllLooperVo;
import com.jili20.vo.user.UserLooperEditVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 轮播图表 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Service
public class LooperServiceImpl extends ServiceImpl<LooperMapper, Looper> implements LooperService {

    @Resource
    private Properties properties;

    /**
     * 门户网站 - 用户 - 新增轮播图片
     * <p>
     * 用户新增、编辑轮播图的时候不删除缓存，因为还要管理员审核，审核通过的时候才删除缓存展示新的数据
     *
     * @param vo
     * @return
     * @CacheEvict(value = "looper", allEntries = true) 删除缓存注解
     */
    @CacheEvict(value = "looper", allEntries = true)
    @Override
    public Result add(LooperIndexVo vo) {
        // 新增轮播图
        final Looper looper = new Looper();
        looper.setUserId(AuthUtil.getAuthUserId());
        looper.setUsername(AuthUtil.getAuthUsername());
        looper.setTitle(vo.getTitle());
        looper.setStatus(ArticleEnum.PASS.getCode());
        looper.setLooperUrl(vo.getLooperUrl());
        looper.setLooperLink(vo.getLooperLink());
        looper.setPosition(vo.getPosition());
        // 插入数据库
        baseMapper.insert(looper);
        return Result.ok().message("投放轮播图片成功");
    }


    /**
     * 门户网站 - 用户 - 编辑轮播图片
     * <p>
     * 用户新增、编辑轮播图的时候不删除缓存，因为还要管理员审核，审核通过的时候才删除缓存展示新的数据
     *
     * @param vo
     * @return
     * @CacheEvict(value = "looper", allEntries = true) 删除缓存注解
     */
    @CacheEvict(value = "looper", allEntries = true)
    @Override
    public Result updateByLooperId(UserLooperEditVo vo) {
        final Looper looper = baseMapper.selectById(vo.getId());
        // 设置更新的内容
        looper.setUserId(AuthUtil.getAuthUserId());
        looper.setUsername(AuthUtil.getAuthUsername());
        looper.setTitle(vo.getTitle());
        looper.setLooperUrl(vo.getLooperUrl());
        looper.setLooperLink(vo.getLooperLink());
        looper.setPosition(vo.getPosition());
        looper.setStatus(StatusEnum.PASS.getCode());
        looper.setUpdateTime(LocalDateTime.now());
        // 执行更新
        final int count = baseMapper.updateById(looper);
        if (count > 0) {
            return Result.ok().message("编辑轮播图成功");
        }
        return Result.error().message("编辑轮播图失败");
    }


    /**
     * 门户网站 - 轮播图 - 审核通过的 - 首页顶部
     * 缓存数据 @Cacheable(value = {"looper"}, key = "#root.methodName", sync = true)
     *
     * @return
     */
    @Cacheable(value = {"looper"}, key = "#root.methodName", sync = true)
    @Override
    public List<LooperVo> looperList() {
        final List<Looper> looperList = baseMapper.selectList(new QueryWrapper<Looper>()
                .eq("position", LoopPositionEnum.INDEX_TOP.getCode())
                .eq("status", StatusEnum.PASS.getCode())
                .select("id", "user_id", "looper_url", "looper_link")
                .orderByDesc("update_time")
                .last("limit 5"));
        // 调用封装的公共方法
        return getLooperVos(looperList);
    }


    /**
     * 门户网站 - 轮播图 - 审核通过的 - 首页右则下
     *
     * @return
     */
    @Cacheable(value = {"looper"}, key = "#root.methodName", sync = true)
    @Override
    public List<LooperVo> looperListIndexRight() {
        final List<Looper> looperList = baseMapper.selectList(new QueryWrapper<Looper>()
                .eq("position", LoopPositionEnum.INDEX_RIGHT.getCode())
                .eq("status", StatusEnum.PASS.getCode())
                .select("id", "title", "user_id", "looper_url", "looper_link")
                .orderByDesc("update_time")
                .last("limit 5"));
        // 调用封装的公共方法
        return getLooperVos(looperList);
    }

    /**
     * 门户网站 - 轮播图 - 审核通过的 - 文章详情中
     *
     * @return
     */
    @Cacheable(value = {"looper"}, key = "#root.methodName", sync = true)
    @Override
    public List<LooperVo> looperListArticle() {
        final List<Looper> looperList = baseMapper.selectList(new QueryWrapper<Looper>()
                .eq("position", LoopPositionEnum.AT_ARTICLE.getCode())
                .eq("status", StatusEnum.PASS.getCode())
                .select("id", "title", "user_id", "looper_url", "looper_link")
                .orderByDesc("update_time")
                .last("limit 5"));
        // 调用封装的公共方法
        return getLooperVos(looperList);
    }


    /**
     * 门户网站 - 轮播图 - 审核通过的 - 赞助详情右则
     *
     * @return
     */
    @Cacheable(value = {"looper"}, key = "#root.methodName", sync = true)
    @Override
    public List<LooperVo> looperListPatronRight() {
        final List<Looper> looperList = baseMapper.selectList(new QueryWrapper<Looper>()
                .eq("position", LoopPositionEnum.INDEX_RIGHT.getCode())
                .eq("status", StatusEnum.PASS.getCode())
                .select("id", "title", "user_id", "looper_url", "looper_link"));
        // 调用封装的公共方法
        return getLooperVos(looperList);
    }


    /**
     * 公共封装方法
     *
     * @param looperList
     * @return
     */
    private List<LooperVo> getLooperVos(List<Looper> looperList) {
        if (looperList != null) {
            List<LooperVo> loopVoList = new ArrayList<>();
            for (Looper looper : looperList) {
                final LooperVo vo = new LooperVo();
                vo.setId(looper.getId());
                vo.setUserId(looper.getUserId());
                vo.setTitle(looper.getTitle());
                vo.setLooperUrl(looper.getLooperUrl());
                vo.setLooperLink(looper.getLooperLink());
                loopVoList.add(vo);
            }
            return loopVoList;
        }
        return null;
    }


    /**
     * 门户网站 - 新增轮播图页面 - 获取前15条轮播图信息
     *
     * @return
     */
    @Cacheable(value = {"looper"}, key = "#root.methodName", sync = true)
    @Override
    public List<LooperTop15Vo> looperTop15List() {
        final List<Looper> looperList = baseMapper.selectList(new QueryWrapper<Looper>()
                .eq("status", StatusEnum.PASS.getCode())
                .select("id", "user_id", "looper_url", "title", "looper_link")
                .orderByDesc("update_time")
                .last("limit 15"));
        // 封装返回类型
        if (looperList != null) {
            List<LooperTop15Vo> looperTop15VoList = new ArrayList<>();
            for (Looper looper : looperList) {
                final LooperTop15Vo vo = new LooperTop15Vo();
                vo.setId(looper.getId());
                vo.setTitle(looper.getTitle());
                vo.setLooperUrl(looper.getLooperUrl());
                vo.setLooperLink(looper.getLooperLink());
                looperTop15VoList.add(vo);
            }
            return looperTop15VoList;
        }
        return null;
    }


    /**
     * 门户网站 - 条件分页查询 - 已审核通过的所有轮播图列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public Result search(Long current, Long size, LooperQuery query) {
        // 组装查询条件
        LambdaQueryWrapper<Looper> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getUsername())) {
            wrapper.like(Looper::getUsername, query.getUsername());
        }
        wrapper.eq(Looper::getStatus, StatusEnum.PASS.getCode());
        wrapper.orderByDesc(Looper::getUpdateTime);
        // 分页查询
        Page<Looper> pageParam = new Page<>(current, size);
        final Page<Looper> looperPageList = baseMapper.selectPage(pageParam, wrapper);
        final List<Looper> records = looperPageList.getRecords();
        final long total = looperPageList.getTotal();
        // 返回vo类型
        if (records != null) {
            List<LooperListVo> looperListVo = new ArrayList<>();
            for (Looper looper : records) {
                final LooperListVo vo = new LooperListVo();
                vo.setId(looper.getId());
                vo.setLooperUrl(looper.getLooperUrl());
                vo.setLooperLink(looper.getLooperLink());
                vo.setPosition(looper.getPosition());
                vo.setTitle(looper.getTitle());
                vo.setUserId(looper.getUserId());
                vo.setUsername(looper.getUsername());
                vo.setCreateTime(looper.getCreateTime());
                looperListVo.add(vo);
            }
            return Result.ok().data("records", looperListVo).data("total", total);
        }
        return null;
    }


    /**
     * 门户网站 - 个人主页 - 用户投图赞助列表
     *
     * @param userId
     * @param current
     * @param size
     * @return
     */
    @Override
    public Result getUserLooperListPage(Long userId, Long current, Long size) {
        // 组装查询条件
        LambdaQueryWrapper<Looper> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Looper::getUserId, userId)
                .in(Looper::getStatus, 1, 3)
                .orderByDesc(Looper::getCreateTime);
        // 分页查询
        final Page<Looper> pageParam = new Page<>(current, size);
        final Page<Looper> looperPage = baseMapper.selectPage(pageParam, wrapper);
        final List<Looper> records = looperPage.getRecords();
        final long total = looperPage.getTotal();
        // 判空
        if (records == null) {
            return null;
        }
        // 封装为VO类型，因为内容跟赞助1元一样，所以复用此 VO
        List<LooperListVo> userLooperListVo = new ArrayList<>();
        for (Looper record : records) {
            final LooperListVo vo = new LooperListVo();
            vo.setId(record.getId());
            vo.setUserId(record.getUserId());
            vo.setUsername(record.getUsername());
            vo.setTitle(record.getTitle());
            vo.setStatus(record.getStatus());
            vo.setLooperUrl(record.getLooperUrl());
            vo.setLooperLink(record.getLooperLink());
            vo.setPosition(record.getPosition());
            vo.setCreateTime(record.getCreateTime());
            userLooperListVo.add(vo);
        }
        return Result.ok().data("records", userLooperListVo).data("total", total);
    }


    /**
     * 门户网站 - 私人空间 - 用户投放的所有轮播图
     *
     * @param userId
     * @param current
     * @param size
     * @return
     */
    @Override
    public Result getUserAllLooperListPage(Long userId, Long current, Long size) {
        // 组装查询条件
        LambdaQueryWrapper<Looper> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Looper::getUserId, userId)
                .orderByDesc(Looper::getCreateTime);
        // 分页查询
        final Page<Looper> pageParam = new Page<>(current, size);
        final Page<Looper> looperPage = baseMapper.selectPage(pageParam, wrapper);
        final List<Looper> records = looperPage.getRecords();
        final long total = looperPage.getTotal();
        // 判空
        if (records == null) {
            return null;
        }
        // 封装为VO类型，因为内容跟赞助1元一样，所以复用此 VO
        List<UserAllLooperVo> userAllLooperVo = new ArrayList<>();
        for (Looper record : records) {
            final UserAllLooperVo vo = new UserAllLooperVo();
            vo.setId(record.getId());
            vo.setUserId(record.getUserId());
            vo.setUsername(record.getUsername());
            vo.setTitle(record.getTitle());
            vo.setStatus(record.getStatus());
            vo.setLooperUrl(record.getLooperUrl());
            vo.setLooperLink(record.getLooperLink());
            vo.setPosition(record.getPosition());
            vo.setCreateTime(record.getCreateTime());
            vo.setUpdateTime(record.getUpdateTime());
            userAllLooperVo.add(vo);
        }
        return Result.ok().data("records", userAllLooperVo).data("total", total);
    }


    /**
     * 门户网站 - 私人空间 - 根据ID删除轮播图
     *
     * @param id
     * @return
     * @CacheEvict(value = "looper", allEntries = true) 删除缓存注解
     */
    @CacheEvict(value = "looper", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Result removeByLooperId(Long id) {
        final Looper looper = baseMapper.selectById(id);
        if (looper == null) {
            return Result.error().message("轮播图不存在");
        }
        // 删除阿里云OSS里的图片
        AliyunUtil.delete(looper.getLooperUrl(), properties.getAliyun());
        // 删除轮播图表数据
        final int count = baseMapper.deleteById(looper);
        if (count > 0) {
            return Result.ok().message("删除轮播图成功");
        }
        return Result.error().message("删除轮播失败");
    }


    /**
     * 后台管理 - 投图赞助管理 - 条件分页查询投图赞助列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public IPage<Looper> selectPage(Long current, Long size, AdminLooperQuery query) {
        LambdaQueryWrapper<Looper> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(query.getId()) && query.getId() > 0, Looper::getId, query.getId());
        wrapper.eq(Objects.nonNull(query.getUserId()) && query.getUserId() > 0, Looper::getUserId, query.getUserId());
        wrapper.like(Objects.nonNull(query.getUsername()), Looper::getUsername, query.getUsername());
        wrapper.like(Objects.nonNull(query.getTitle()), Looper::getTitle, query.getTitle());
        wrapper.like(Objects.nonNull(query.getStatus()), Looper::getStatus, query.getStatus());
        wrapper.like(Objects.nonNull(query.getLooperLink()), Looper::getLooperLink, query.getLooperLink());
        wrapper.like(Objects.nonNull(query.getPosition()), Looper::getPosition, query.getPosition());
        wrapper.ge(Objects.nonNull(query.getBegin()), Looper::getCreateTime, query.getBegin());
        wrapper.le(Objects.nonNull(query.getEnd()), Looper::getCreateTime, query.getEnd());
        wrapper.orderByAsc(Looper::getStatus);
        wrapper.orderByDesc(Looper::getCreateTime);
        // 分页查询
        final Page<Looper> pageParam = new Page<>(current, size);
        final Page<Looper> looperPage = baseMapper.selectPage(pageParam, wrapper);
        final List<Looper> records = looperPage.getRecords();
        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 后台管理 - 投图赞助管理 - 图片审核：通过 / 不通过
     *
     * @param id      图片ID
     * @param status  状态
     * @return
     * @CacheEvict(value = "looper", allEntries = true) 删除轮播图缓存
     */
    @CacheEvict(value = "looper", allEntries = true)
    @Override
    public Result updateStatus(Long id, String status) {
        final Looper looper = baseMapper.selectById(id);
        if (looper == null) {
            return Result.error().message("该图片不存在");
        }
        looper.setStatus(status);
        looper.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(looper);
        return Result.ok().message("操作成功");
    }


    /**
     * 后台管理 - 首页 - 统计投图赞助总记录数
     *
     * @return
     */
    @Override
    public Result getTotal() {
        final Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Looper>()
                .eq(Looper::getStatus, StatusEnum.PASS.getCode()));
        return Result.ok().data("loopTotal", count);
    }

}
