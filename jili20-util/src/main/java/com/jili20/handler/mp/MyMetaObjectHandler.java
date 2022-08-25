package com.jili20.handler.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * MybatisPlush - 自动更新版本号配置
 *
 * @author bing_  @create 2022/1/19-15:06
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 使用 mp实现添加操作，这个方法执行
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动插入初始值
        this.setFieldValByName("version", 1, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {}
}
