package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jili20.entity.Announcement;
import com.jili20.enums.AnnEnum;
import com.jili20.mapper.AnnouncementMapper;
import com.jili20.result.Result;
import com.jili20.service.AnnouncementService;
import com.jili20.util.AuthUtil;
import com.jili20.vo.admin.AnnouncementAddVo;
import com.jili20.vo.admin.AnnouncementQuery;
import com.jili20.vo.admin.AnnouncementUpdateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-04-18
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Resource
    private AnnouncementService announcementService;

    /**
     * 后台管理 - 管理员新增公告
     *
     * @param vo
     * @return
     */
    @Override
    public Result add(AnnouncementAddVo vo) {
        final Announcement announcement = new Announcement();
        announcement.setUserId(AuthUtil.getAuthUserId());
        announcement.setContent(vo.getContent());
        announcement.setStatus(AnnEnum.WAIT.getCode());
        announcement.setCreateTime(LocalDateTime.now());
        announcement.setCreateTime(LocalDateTime.now());
        final int result = baseMapper.insert(announcement);
        if (result > 0) {
            return Result.ok().message("新增公告成功");
        }
        return Result.error().message("新增公告失败，请重试");
    }


    /**
     * 后台管理 - 编辑公告
     *
     * @param vo
     * @return
     */
    @CacheEvict(value = "announcement", allEntries = true)
    @Override
    public Result updateByAnnId(AnnouncementUpdateVo vo) {
        final Announcement announcement = baseMapper.selectById(vo.getId());
        announcement.setContent(vo.getContent());
        announcement.setUpdateTime(LocalDateTime.now());
        final int result = baseMapper.updateById(announcement);
        if (result > 0) {
            return Result.ok().message("编辑公告成功");
        }
        return Result.error().message("编辑公司失败，请重试");
    }


    /**
     * 后台管理 - 修改公告的状态（1 展示中；2 已过期）
     *
     * @param id
     * @param status
     * @return
     */
    @CacheEvict(value = "announcement", allEntries = true)
    @Override
    public Result updateStatusById(Integer id, String status) {
        final Announcement announcement = baseMapper.selectById(id);
        announcement.setStatus(status);
        baseMapper.updateById(announcement);
        // 主要在前端不同调用方法中返回具体成功信息
        return Result.ok().message("修改公告状态成功");
    }


    /**
     * 后台管理 - 公告管理 - 条件分页查询公告列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public IPage<Announcement> search(Long current, Long size, AnnouncementQuery query) {
        // 组装查询条件
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(query.getId()) && query.getId() > 0, Announcement::getId, query.getId());
        wrapper.eq(Objects.nonNull(query.getUserId()) && query.getUserId() > 0, Announcement::getUserId, query.getUserId());
        wrapper.like(Objects.nonNull(query.getContent()), Announcement::getContent, query.getContent());
        wrapper.eq(Objects.nonNull(query.getStatus()), Announcement::getStatus, query.getStatus());
        wrapper.ge(Objects.nonNull(query.getBegin()), Announcement::getCreateTime, query.getBegin());
        wrapper.le(Objects.nonNull(query.getEnd()), Announcement::getCreateTime, query.getEnd());
        // 状态：(0 待用；1 展示中；2 已过期；)
        wrapper.orderByAsc(Announcement::getStatus);
        wrapper.orderByDesc(Announcement::getCreateTime);
        // 分页查询
        final Page<Announcement> pageParam = new Page<>(current, size);
        final Page<Announcement> announcementPage = baseMapper.selectPage(pageParam, wrapper);
        final List<Announcement> records = announcementPage.getRecords();
        return pageParam.setRecords(records);
    }


    /**
     * 门户网站 - 查询最新一条展示中公告
     *
     * @return
     */
    @Cacheable(value = {"announcement"}, key = "#root.methodName", sync = true)
    @Override
    public Result getInUseOne() {
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        wrapper.eq("status", AnnEnum.USE.getCode())
                .orderByDesc("update_time");
        final String content = announcementService.getOnly(wrapper).getContent();
        if (StringUtils.isEmpty(content)) {
            return Result.ok();
        } else {
            return Result.ok().data("content", content);
        }
    }
}
