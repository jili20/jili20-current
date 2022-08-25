package com.jili20.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jili20.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限菜单表 Mapper 接口
 * </p>
 *
 * @author Bing
 * @since 2022-01-09
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 登录 - 获取用户权限列表
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(@Param("userId") Long userId);

}
