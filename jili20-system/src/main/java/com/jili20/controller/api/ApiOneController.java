package com.jili20.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.entity.One;
import com.jili20.result.Result;
import com.jili20.service.OneService;
import com.jili20.vo.api.OneQueryVo;
import com.jili20.vo.api.OneTopVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 投放诗语表（个人网站版） 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-04-29
 */
@Api(tags = "API - 投放诗语管理（个人网站版）")
@RestController
@RequestMapping("/api/one")
public class ApiOneController {

    @Resource
    private OneService oneService;


    @ApiOperation("门户网站 - 首页 - 查询前 30 位1元赞助人")
    @GetMapping("/top")
    public Result getOneTop() {
        List<OneTopVo> oneTop = oneService.getOneTop();
        return Result.ok().data("oneTop", oneTop);
    }


    @ApiOperation("门户网站 - 条件分页查询 - 所有投放诗语列表")
    @GetMapping("/one/list/{current}/{size}")
    public Result searchOne(@PathVariable Long current, @PathVariable Long size,
                            OneQueryVo query) {
        IPage<One> pageModel = oneService.listPage(current, size, query);
        List<One> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return Result.ok().data("records", records).data("total", total);
    }

}