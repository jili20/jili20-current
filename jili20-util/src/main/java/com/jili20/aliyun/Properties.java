package com.jili20.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * @author bing_
 */
@Data
@Component
@ConfigurationProperties(prefix = "jili20.oss")
public class Properties implements Serializable {

    /**
     * 会将 aliyun.oss 下的配置绑定到 AliyunProperties 对象属性上
     */
    private AliyunProperties aliyun;

}
