package com.jili20.controller.api;

import com.jili20.result.Result;
import com.jili20.service.CategoryService;
import com.jili20.vo.api.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文章分类表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "API - 分类管理")
@RestController
@RequestMapping("/api/category")
public class ApiCategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation("分类管理 - 查询所有分类 - 组装父子结构")
    @GetMapping("/search") 
    public Result list() {
        List<CategoryVo> categoryList = categoryService.listWithTree();
        return Result.ok().data("categoryList", categoryList);
    }

}

