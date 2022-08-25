package com.jili20.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.entity.Announcement;
import com.jili20.result.Result;
import com.jili20.vo.admin.AnnouncementAddVo;
import com.jili20.vo.admin.AnnouncementQuery;
import com.jili20.vo.admin.AnnouncementUpdateVo;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-04-18
 */
public interface AnnouncementService extends IService<Announcement> {

    /**
     * 后台管理 - 管理员新增公告
     * @param vo
     * @return
     */
    Result add(AnnouncementAddVo vo);


    /**
     * 后台管理 - 编辑公告
     * @param vo
     * @return
     */
    Result updateByAnnId(AnnouncementUpdateVo vo);


    /**
     * 后台管理 - 修改公告的状态（1 展示中；2 已过期）
     * @param id
     * @param status
     * @return
     */
    Result updateStatusById(Integer id, String status);


    /**
     * 后台管理 - 公告管理 - 条件分页查询公告列表
     * @param current
     * @param size
     * @param query
     * @return
     */
    IPage<Announcement> search(Long current, Long size, AnnouncementQuery query);

    /**
     * 门户网站 - 查询最新一条展示中公告
     * @return
     */
    Result getInUseOne();


    /**
     * 门户网站 - 查询最新一条展示中公告 - 获取一条数据
     * @param wrapper
     * @return
     */
    default Announcement getOnly(QueryWrapper<Announcement> wrapper) {
        wrapper.last("limit 1");
        return this.getOne(wrapper);
    }
}
