package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jili20.aliyun.AliyunUtil;
import com.jili20.aliyun.Properties;
import com.jili20.entity.One;
import com.jili20.mapper.OneMapper;
import com.jili20.result.Result;
import com.jili20.service.OneService;
import com.jili20.vo.api.OneQueryVo;
import com.jili20.vo.api.OneTopVo;
import com.jili20.vo.user.OneAddVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 投放诗语表（个人网站版） 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-04-29
 */
@Slf4j
@Service
public class OneServiceImpl extends ServiceImpl<OneMapper, One> implements OneService {

    @Resource
    private Properties properties;

    /**
     * 门户网站 - 首页 - 新增 - 投放诗语
     *
     * @param vo
     * @return
     */
    @CacheEvict(value = "one", allEntries = true)
    @Override
    public Result add(OneAddVo vo) {
        final One one = new One();
        one.setAvatar(vo.getAvatar());
        one.setUsername(vo.getUsername());
        one.setMessage(vo.getMessage());
        one.setLink(vo.getLink());
        one.setCreateTime(LocalDateTime.now());
        baseMapper.insert(one);
        return Result.ok().message("新增随机诗语成功");
    }


    /**
     * 门户网站 - 首页 - 查询前12位1元赞助人
     *
     * @return
     */
    @Cacheable(value = {"one"}, key = "#root.methodName", sync = true)
    @Override
    public List<OneTopVo> getOneTop() {
        final List<One> oneList = baseMapper.selectList(new QueryWrapper<One>()
                .select("avatar", "link")
                .orderByDesc("create_time")
                .last("limit 30"));
        // 转换为VO类型，遍历拷贝赋值
        if (oneList != null) {
            List<OneTopVo> oneVoList = new ArrayList<>();
            for (One one : oneList) {
                final OneTopVo vo = new OneTopVo();
                vo.setAvatar(one.getAvatar());
                vo.setLink(one.getLink());
                oneVoList.add(vo);
            }
            return oneVoList;
        }
        return null;
    }


    /**
     * 门户网站 - 条件分页查询 - 所有投放诗语列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public IPage<One> listPage(Long current, Long size, OneQueryVo query) {
        LambdaQueryWrapper<One> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(query.getUsername()), One::getUsername, query.getUsername());
        wrapper.orderByDesc(One::getCreateTime);
        // 分页查询
        final Page<One> pageParam = new Page<>(current, size);
        final Page<One> onePage = baseMapper.selectPage(pageParam, wrapper);
        final List<One> records = onePage.getRecords();
        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 门户网站 - 通知详情页面 - 根据ID删除通知
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "one", allEntries = true)
    @Override
    public Result removeByOneId(Long id) {
        final One one = baseMapper.selectById(id);
        final String avatar = one.getAvatar();
        // 删除阿里云OSS里的图片
        try {
            AliyunUtil.delete(avatar, properties.getAliyun());
        } catch (Exception e) {
             log.info("不是阿里云的图片或删除失败");
        }
        // 删除投放诗语表数据
        final int count = baseMapper.deleteById(id);
        if (count > 0) {
            return Result.ok().message("删除成功");
        }
        return Result.error().message("删除失败");
    }
}
