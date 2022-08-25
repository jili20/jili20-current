package com.jili20.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.entity.Aphorism;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.AphorismService;
import com.jili20.vo.admin.AphorismAddVo;
import com.jili20.vo.admin.AphorismQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 随机诗语 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "Admin - 随机诗语管理")
@RestController
@RequestMapping("/aphorism")
public class AphorismController {


    @Resource
    private AphorismService aphorismService;


    //@PreAuthorize("hasAuthority('aphorism:search')")
    @ApiOperation("后台管理 - 随机诗语 - 条件分页查询随机诗语列表")
    @PostMapping("/search/{current}/{size}")
    public Result search(@ApiParam(value = "当前页码", required = true) @PathVariable Long current,
                         @ApiParam(value = "每页记录数", required = true) @PathVariable Long size,
                         @RequestBody AphorismQuery query) {
        IPage<Aphorism> pageModel = aphorismService.search(current, size, query);
        List<Aphorism> records = pageModel.getRecords();
        long total = pageModel.getTotal();

        return Result.ok().data("records", records).data("total", total);
    }


    @ApiOperation("后台管理 - 随机诗语 - 新增")
    //@PreAuthorize("hasAuthority('aphorism:add')")
    @PostMapping("/add")
    public Result add(@RequestBody AphorismAddVo vo) {
        final String content = vo.getContent();
        final String author = vo.getAuthor();
        Assert.notEmpty(content, ResponseEnum.CONTENT_NULL_ERROR);
        Assert.isTrue(content.length() >= 1 && content.length() <= 255, ResponseEnum.CONTENT_LENGTH_ERROR);
        if (StringUtils.isNotBlank(author)) {
            Assert.isTrue(author.length() >= 1 && author.length() <= 20, ResponseEnum.AUTHOR_LENGTH_ERROR);
        }
        return aphorismService.add(vo);
    }


    @ApiOperation("后台管理 - 随机诗语 - 根据ID查询详情")
    //@PreAuthorize("hasAuthority('aphorism:view')")
    @GetMapping("/view/{id}")
    public Result view(@PathVariable("id") Integer id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        final Aphorism aphorism = aphorismService.getById(id);
        return Result.ok().data("aphorism", aphorism);
    }


    @ApiOperation("后台管理 - 随机诗语 - 根据ID编辑")
    //@PreAuthorize("hasAuthority('aphorism:edit')")
    @PutMapping("/update")
    public Result updateById(@RequestBody AphorismAddVo vo) {
        final String content = vo.getContent();
        final String author = vo.getAuthor();
        Assert.notEmpty(content, ResponseEnum.CONTENT_NULL_ERROR);
        Assert.isTrue(content.length() >= 1 && content.length() <= 255, ResponseEnum.CONTENT_LENGTH_ERROR);
        if (StringUtils.isNotBlank(author)) {
            Assert.isTrue(author.length() >= 1 && author.length() <= 20, ResponseEnum.AUTHOR_LENGTH_ERROR);
        }
        return aphorismService.updateByAphorismId(vo);
    }


    @ApiOperation("后台管理 - 随机诗语 - 根据ID删除")
    @PreAuthorize("hasAuthority('aphorism:delete')")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable("id") Integer id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        final boolean result = aphorismService.removeById(id);
        if (result) {
            return Result.ok().message("删除成功");
        }
        return Result.error().message("删除失败，请重试");
    }

}

