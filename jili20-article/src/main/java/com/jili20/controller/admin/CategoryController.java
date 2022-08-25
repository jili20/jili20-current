package com.jili20.controller.admin;

import com.jili20.entity.Category;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.CategoryService;
import com.jili20.vo.api.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 文章分类表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "Admin - 分类管理")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation("后台管理 - 分类管理 - 查询所有分类 - 组装父子结构")
    @PreAuthorize("hasAuthority('category:view')")
    @GetMapping("/search")
    public Result list() {
        List<CategoryVo> categoryList = categoryService.listWithTree();
        return Result.ok().data("categoryList", categoryList);
    }


    @ApiOperation("后台管理 - 分类管理 - 新增分类")
    @PreAuthorize("hasAuthority('category:add')")
    @PostMapping("/save")
    public Result add(@ApiParam(value = "分类对象", required = true)
                      @RequestBody Category category) {
        // 判空
        Assert.notEmpty(category.getCategoryName(), ResponseEnum.CATEGORY_NAME_NULL_ERROR);
        // 限制长度
        Assert.isTrue(category.getCategoryName().length() >= 2 && category.getCategoryName().length() <= 10, ResponseEnum.CATEGORY_NAME_LENGTH_ERROR);
        // 执行新增
        return categoryService.add(category);
    }


    @ApiOperation("后台管理 - 分类管理 - 根据ID查询分类信息")
    @PreAuthorize("hasAuthority('category:view')")
    @GetMapping("/{id}")
    public Result view(@PathVariable("id") Integer id) {
        Category category = categoryService.getById(id);
        return Result.ok().data("category", category);
    }


    @ApiOperation("后台管理 - 分类管理 - 编辑分类")
    @PreAuthorize("hasAuthority('category:edit')")
    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        if (null == category.getId()) {
            return Result.error().message("分类ID不能为空");
        }
        // 判空
        Assert.notEmpty(category.getCategoryName(), ResponseEnum.CATEGORY_NAME_NULL_ERROR);
        // 限制长度
        Assert.isTrue(category.getCategoryName().length() >= 2 && category.getCategoryName().length() <= 10, ResponseEnum.CATEGORY_NAME_LENGTH_ERROR);
        // 执行编辑
        return categoryService.updateCategory(category);
    }


    @ApiOperation("后台管理 - 分类管理 - 批量删除分类")
    @PreAuthorize("hasAuthority('category:delete')")
    @DeleteMapping("/remove")
    public Result remove(@RequestBody Integer[] categoryIds) {
        if (null == categoryIds) {
            return Result.error().message("分类ID不能为空");
        }
        return categoryService.removeByCategoryIds(Arrays.asList(categoryIds));
    }

    /**
     * @CacheEvict(value = "category", allEntries = true) 删除 category 分区所有缓存
     */
    @ApiOperation("后台管理 - 分类管理 - 修改分类顺序")
    @PreAuthorize("hasAuthority('category:edit')")
    @CacheEvict(value = "category", allEntries = true)
    @PutMapping("/update/sort")
    public Result updateSort(@RequestBody Category[] category) {
        // 调用框架方法批量修改分类顺序
        categoryService.updateBatchById(Arrays.asList(category));
        return Result.ok().message("修改分类顺序成功");
    }
}

