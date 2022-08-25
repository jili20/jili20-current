package com.jili20.util;

/**
 * 校验上传图片的类型
 *
 * @author bing_  @create 2022/4/27-18:08
 */
public interface ImageType {

    String PREFIX = "image/";
    String JPG = "jpg";
    String JPEG = "jpeg";
    String PNG = "png";
    String GIF = "gif";
    String JPG_WITH_PREFIX = PREFIX + "jpg";
    String JPEG_WITH_PREFIX = PREFIX + "jpeg";
    String PNG_WITH_PREFIX = PREFIX + "png";
    String GIF_WITH_PREFIX = PREFIX + "gif";
}
