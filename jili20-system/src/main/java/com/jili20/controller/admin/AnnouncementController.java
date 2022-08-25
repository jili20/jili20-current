package com.jili20.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.entity.Announcement;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.AnnouncementService;
import com.jili20.util.RegexValidateUtils;
import com.jili20.vo.admin.AnnouncementAddVo;
import com.jili20.vo.admin.AnnouncementQuery;
import com.jili20.vo.admin.AnnouncementUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-04-18
 */
@Api(tags = "Admin - 公告管理")
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;


    @ApiOperation("后台管理 - 管理员新增公告")
    @PreAuthorize("hasAuthority('announcement:add')")
    @PostMapping
    public Result add(@RequestBody AnnouncementAddVo vo) {
        final String content = vo.getContent();
        Assert.notEmpty(content, ResponseEnum.CONTENT_NULL_ERROR);
        Assert.isTrue(content.length() > 9 && content.length() < 256, ResponseEnum.ANNOUNCEMENT_CONTENT_LENGTH_ERROR);
        return announcementService.add(vo);
    }


    @ApiOperation("后台管理 - 根据ID查询公告详情")
    @PreAuthorize("hasAuthority('announcement:view')")
    @GetMapping("/{id}")
    public Result view(@PathVariable("id") Integer id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        final Announcement announcement = announcementService.getById(id);
        Assert.notNull(announcement, ResponseEnum.ANNOUNCEMENT_NULL_ERROR);
        return Result.ok().data("announcement", announcement);
    }


    @ApiOperation("后台管理 - 编辑公告")
    @PreAuthorize("hasAuthority('announcement:edit')")
    @PutMapping
    public Result update(@RequestBody AnnouncementUpdateVo vo) {
        final String content = vo.getContent();
        Assert.notEmpty(content, ResponseEnum.CONTENT_NULL_ERROR);
        Assert.isTrue(content.length() > 9 && content.length() < 256, ResponseEnum.ANNOUNCEMENT_CONTENT_LENGTH_ERROR);
        return announcementService.updateByAnnId(vo);
    }


    @ApiOperation("后台管理 - 根据ID删除公告")
    @PreAuthorize("hasAuthority('announcement:delete')")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        final boolean result = announcementService.removeById(id);
        if (result) {
            return Result.ok().message("删除公告成功");
        }
        return Result.error().message("该公告不存在");
    }


    @ApiOperation("后台管理 - 修改公告的状态（1 展示中；2 已过期）")
    @PreAuthorize("hasAuthority('announcement:edit')")
    @GetMapping("/update/status/{id}/{status}")
    public Result updateStatus(@PathVariable("id") Integer id, @PathVariable("status") String status) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        Assert.notEmpty(status, ResponseEnum.STATUS_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkLength1(status), ResponseEnum.STATUS_LENGTH_ERROR);
        return announcementService.updateStatusById(id, status);
    }


    @ApiOperation("后台管理 - 条件分页查询公告列表")
    @PreAuthorize("hasAuthority('announcement:search')")
    @PostMapping("/admin/search/{current}/{size}")
    public Result search(@PathVariable Long current, @PathVariable Long size,
                         @RequestBody AnnouncementQuery query) {
        IPage<Announcement> pageModel = announcementService.search(current, size, query);
        List<Announcement> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return Result.ok().data("records", records).data("total", total);
    }


}

