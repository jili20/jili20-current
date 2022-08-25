package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jili20.entity.Article;
import com.jili20.entity.Category;
import com.jili20.exception.Assert;
import com.jili20.mapper.CategoryMapper;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.CategoryService;
import com.jili20.vo.api.CategoryVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章分类表 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Resource
    private ArticleServiceImpl articleService;


    /**
     * 后台管理 - 分类管理 - 查询所有分类 - 组装父子结构
     * 只发一个sql语句，查出所有的菜单，再利用递归的方式来组合出父子菜单
     * <p>
     * Cacheable(value = {"category"}, key = "#root.methodName", sync = true) 加缓存加锁
     */
    @Cacheable(value = {"category"}, key = "#root.methodName", sync = true)
    @Override
    public List<CategoryVo> listWithTree() {
        // 查出所有分类
        List<Category> categories = baseMapper.selectList(new QueryWrapper<Category>().select("id", "parent_id", "category_name", "sort"));
        // 组装父子结构
        // 1）找出所有一级分类
        final List<Category> categoryList = categories.stream().filter(category -> category.getParentId() == 0).map((category) -> {
            // 重新映射每一个分类，调用下面递归方法，查找和封装子分类
            category.setChildren(getChildrens(category, categories));
            // 返回所有子分类
            return category;
        }).sorted((cat1, cat2) -> {
            // 分类排序
            return (cat1.getSort() == null ? 0 : cat1.getSort() - (cat2.getSort() == null ? 0 : cat2.getSort()));
        }).collect(Collectors.toList());

        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            final CategoryVo vo = new CategoryVo();
            vo.setId(category.getId());
            vo.setParentId(category.getParentId());
            vo.setCategoryName(category.getCategoryName());
            // 将子分类也封装为 CategoryVo 类型
            List<CategoryVo> cVoList = new ArrayList<>();
            for (Category child : category.getChildren()) {
                final CategoryVo cVo = new CategoryVo();
                cVo.setId(child.getId());
                cVo.setCategoryName(child.getCategoryName());
                cVo.setParentId(child.getParentId());
                cVoList.add(cVo);
            }
            vo.setChildren(cVoList);
            categoryVoList.add(vo);
        }
        return categoryVoList;
    }


    /**
     * 辅助方法 - 递归查询所有子分类
     *
     * @param root
     * @param categories
     * @return
     */
    private List<Category> getChildrens(Category root, List<Category> categories) {
        return categories.stream().filter(category -> {
            // 查出当前分类父分类ID
            return category.getParentId().equals(root.getId());
        }).map(category -> {
            // 重新映射每一个分类，递归继续查找子分类
            category.setChildren(getChildrens(category, categories));
            // 返回子分类
            return category;
        }).sorted((cat1, cat2) -> {
            // 分类排序
            return (cat1.getSort() == null ? 0 : cat1.getSort() - (cat2.getSort() == null ? 0 : cat2.getSort()));
        }).collect(Collectors.toList());
    }


    /**
     * 后台管理 - 分类管理 - 新增分类
     *
     * @param category / CacheEvict(value = " category ", allEntries = true) 删除 category 分区所有缓存
     */
    @CacheEvict(value = "category", allEntries = true)
    @Override
    public Result add(Category category) {
        // 校验分类名称是否已存在
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Category>().eq(Category::getCategoryName, category.getCategoryName()));
        if (count > 0) {
            return Result.error().message("分类名称已存在");
        }
        baseMapper.insert(category);
        return Result.ok().message("新增分类成功");
    }


    /**
     * 后台管理 - 分类管理 - 编辑分类
     * CacheEvict(value = " category ", allEntries = true) 删除 category 分区所有缓存
     */
    @CacheEvict(value = "category", allEntries = true)
    @Override
    public Result updateCategory(Category category) {
        // 查出正在编辑的分类
        Category dbCategory = baseMapper.selectById(category.getId());
        // 分类不存在
        Assert.notNull(dbCategory, ResponseEnum.CATEGORY_EXIST_ERROR);
        // 分类存在，校验新分类名是否与其它的相同，相同给错误提示
        if (!dbCategory.getCategoryName().equals(category.getCategoryName())) {
            Category categoryName = baseMapper.selectOne(new QueryWrapper<Category>().eq("category_name", category.getCategoryName()));
            Assert.isNull(categoryName, ResponseEnum.CATEGORY_NAME_EXIST_ERROR);
        }
        // 执行更新
        dbCategory.setCategoryName(category.getCategoryName());
        dbCategory.setUpdateTime(LocalDateTime.now());
        // 更新数据库里的信息
        baseMapper.updateById(dbCategory);
        // 返回结果
        return Result.ok().message("编辑分类成功");
    }


    /**
     * 后台管理 - 分类管理 - 批量删除分类
     * CacheEvict(value = " category ", allEntries = true) 删除 category 分区所有缓存
     */
    @CacheEvict(value = "category", allEntries = true)
    @Override
    public Result removeByCategoryIds(List<Integer> asList) {
        // 检查当前要删除的分类是否被别的地方引用，有引用不能删除
        final List<Article> articleList = articleService.list(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, asList)
                .or()
                .in(Article::getCategoryPid, asList));
        if (!articleList.isEmpty()) {
            return Result.error().message("该分类已被引用，不能删除");
        }
        int count = baseMapper.deleteBatchIds(asList);
        if (count > 0) {
            return Result.ok().message("删除分类成功");
        }
        return Result.error().message("删除分类失败");
    }


}
