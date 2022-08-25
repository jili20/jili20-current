package com.jili20.aliyun;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bing_  @create 2022/1/21-16:44
 */
@Data
public class AliyunProperties implements Serializable {

    /**
     * oss的端点信息
     */
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    /**
     * 存储空间名称
     */
    private String bucketName;
    /**
     * Bucket名称, 访问文件时作为基础URL
     */
    private String bucketDomain;

}
