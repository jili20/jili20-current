package com.jili20.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.entity.Background;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.BackgroundService;
import com.jili20.vo.admin.BackgroundAddVo;
import com.jili20.vo.admin.BackgroundQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 轮播图背景图表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-04-02
 */
@Api(tags = "Admin - 轮播图背景图管理")
@RestController
@RequestMapping("/background")
public class BackgroundController {


    @Resource
    private BackgroundService backgroundService;


    @ApiOperation("后台管理 - 轮播图背景图管理 - 新增首页轮播图背景图片")
    @PreAuthorize("hasAuthority('background:add')")
    @PostMapping("/add")
    public Result add(@RequestBody BackgroundAddVo vo) {
        final String url = vo.getUrl();
        Assert.notEmpty(url, ResponseEnum.BACKGROUND_URL_NULL_ERROR);
        Assert.isTrue(url.length() > 10 && url.length() <= 255, ResponseEnum.BACKGROUND_URL_LENGTH_ERROR);
        return backgroundService.add(vo);
    }


    @ApiOperation("后台管理 - 轮播图背景图管理 - 条件分页查询轮播图背景图列表")
    @PreAuthorize("hasAuthority('background:search')")
    @PostMapping("/search/{current}/{size}")
    public Result search(@ApiParam(value = "当前页码", required = true) @PathVariable Long current,
                         @ApiParam(value = "每页记录数", required = true) @PathVariable Long size,
                         @RequestBody BackgroundQuery query) {
        IPage<Background> pageModel = backgroundService.selectPage(current, size, query);
        List<Background> records = pageModel.getRecords();
        long total = pageModel.getTotal();

        return Result.ok().data("records", records).data("total", total);
    }


    @ApiOperation("后台管理 - 轮播图背景图管理 - 设置为启用")
    @PreAuthorize("hasAuthority('background:edit')")
    @GetMapping("/working/{id}")
    public Result working(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        // 设置状态为 启用
        final String status = Background.STATUS_WORKING;
        return backgroundService.updateStatus(id, status);
    }


    @ApiOperation("后台管理 - 轮播图背景图管理 - 设置为停用")
    @PreAuthorize("hasAuthority('background:edit')")
    @GetMapping("/stop/{id}")
    public Result stop(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        // 设置状态为 停用
        final String status = Background.STATUS_STOP;
        return backgroundService.updateStatus(id, status);
    }


    @ApiOperation("后台管理 - 轮播图背景图管理 - 根据ID删除背景图片")
    @PreAuthorize("hasAuthority('background:delete')")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        // 设置状态为 启用
        return backgroundService.removeByBackgroundId(id);
    }


}

