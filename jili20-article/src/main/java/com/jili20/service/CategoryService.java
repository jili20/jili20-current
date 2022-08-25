package com.jili20.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.entity.Category;
import com.jili20.result.Result;
import com.jili20.vo.api.CategoryVo;

import java.util.List;

/**
 * <p>
 * 文章分类表 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
public interface CategoryService extends IService<Category> {


    /**
     * 后台管理 - 分类管理 - 查询所有分类 - 组装父子结构
     * @return
     */
    List<CategoryVo> listWithTree();

    /**
     * 后台管理 - 分类管理 - 新增分类
     * @param category
     * @return
     */
    Result add(Category category);


    /**
     * 后台管理 - 分类管理 - 编辑分类
     * @param category
     * @return
     */
    Result updateCategory(Category category);


    /**
     * 后台管理 - 分类管理 - 批量删除分类
     * @param asList
     * @return
     */
    Result removeByCategoryIds(List<Integer> asList);


}
