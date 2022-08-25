package com.jili20.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.entity.Looper;
import com.jili20.enums.StatusEnum;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.LooperService;
import com.jili20.vo.admin.AdminLooperQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 轮播图表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "Admin- 投图赞助管理")
@RestController
@RequestMapping("/looper")
public class LooperController {


    @Resource
    private LooperService looperService;


    @PreAuthorize("hasAuthority('looper:search')")
    @ApiOperation("后台管理 - 投图赞助管理 - 条件分页查询投图赞助列表")
    @PostMapping("/search/{current}/{size}")
    public Result search(@ApiParam(value = "当前页码", required = true) @PathVariable Long current,
                         @ApiParam(value = "每页记录数", required = true) @PathVariable Long size,
                         @RequestBody AdminLooperQuery query) {
        IPage<Looper> pageModel = looperService.selectPage(current, size, query);
        List<Looper> records = pageModel.getRecords();
        long total = pageModel.getTotal();

        return Result.ok().data("records", records).data("total", total);
    }


    @PreAuthorize("hasAuthority('looper:audit')")
    @ApiOperation("后台管理 - 投图赞助管理 - 图片审核通过")
    @GetMapping("/pass/{id}")
    public Result auditPass(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        // 审核通过 1
        String status = StatusEnum.PASS.getCode();
        // 审核不通过原因 无 0
        return looperService.updateStatus(id, status);
    }


    @PreAuthorize("hasAuthority('looper:audit')")
    @ApiOperation("后台管理 - 投图赞助管理 - 图片审核未通过")
    @GetMapping("/fail/{id}")
    public Result auditNoPass(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        // 审核不通过 2
        String status = StatusEnum.NO_PASS.getCode();
        return looperService.updateStatus(id, status);
    }


    @ApiOperation("门户网站 - 私人空间 - 根据ID删除轮播图")
    @PreAuthorize("hasAuthority('looper:delete')")
    @DeleteMapping("/admin/remove/{id}")
    public Result removeByLooperId(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        return looperService.removeByLooperId(id);
    }

    @ApiOperation("后台管理 - 首页 - 统计投图赞助总记录数")
    @PreAuthorize("hasAuthority('index')")
    @GetMapping("/total")
    public Result total() {
        return looperService.getTotal();
    }

}

