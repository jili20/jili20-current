package com.jili20.controller.user;

import com.jili20.aliyun.AliyunProperties;
import com.jili20.aliyun.AliyunUtil;
import com.jili20.aliyun.Properties;
import com.jili20.enums.UserStatusEnum;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.util.AuthUtil;
import com.jili20.util.ImageType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 上传图片 - 控制器
 *
 * @author bing_  @create 2022/1/21-17:13
 */
@Api(tags = "阿里云 - OSS文件管理 - 上传或删除图片")
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @Resource
    private Properties properties;


    @ApiOperation("上传 - 图片")
    @PostMapping("/{type}")
    public Result upload(@PathVariable("type") String type, @RequestParam("file") MultipartFile file) {
        // 检查当前登录用户状态，1 为已被禁言
        final Long userId = AuthUtil.getAuthUserId();
        final String status = AuthUtil.getAuthUserStatus();
        Assert.notNull(userId, ResponseEnum.ACCOUNT_NOT_LOGIN);
        if (UserStatusEnum.FORBIDDEN_SPEAK.getCode().equals(status)) {
            return Result.setResult(ResponseEnum.USER_STATUS_FORBIDDEN_ARTICLE);
        }
        // 判断是否有文件
        if (file == null) {
            return Result.error().message("图片不可以为空");
        }
        // 判断文件类型，我们只支持图片上传，比如说：png、jpg、jpeg、gif
        String contentType = file.getContentType();

        if (StringUtils.isEmpty(contentType)) {
            return Result.error().message("图片格式错误");
        }
        //图片类型常量：png、jpg、gif
        //根据我们定的规则进行命名
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型（方法抽取）
        String photoType = getType(contentType, originalFilename);
        if (photoType == null) {
            return Result.error().message("不支持上传此格式的图片");
        }
        // 获取阿里云 OSS 相关配置信息
        AliyunProperties aliyun = properties.getAliyun();
        // 上传文件（参数：文件类型、文件、配置信息）这里是文章类型
        return AliyunUtil.uploadFileToOss(type, file, aliyun);
    }


    /**
     * 上传 - 图片 - 辅助方法 - 获取文件格式
     * 获取文件的后缀名 统一转为小写再进行判断
     *
     * @param contentType
     * @param name
     * @return
     */
    private String getType(String contentType, String name) {
        String type = null;
        if (ImageType.PNG_WITH_PREFIX.equals(contentType) &&
                name.toLowerCase().endsWith(ImageType.PNG)) {
            type = ImageType.PNG;
        } else if (ImageType.JPG_WITH_PREFIX.equals(contentType) &&
                name.toLowerCase().endsWith(ImageType.JPG)) {
            type = ImageType.JPG;
        } else if (ImageType.GIF_WITH_PREFIX.equals(contentType) &&
                name.toLowerCase().endsWith(ImageType.GIF)) {
            type = ImageType.GIF;
            // 这里处理上传不了 jpg 格式图片
        } else if (ImageType.JPEG_WITH_PREFIX.equals(contentType) &&
                name.toLowerCase().endsWith(ImageType.JPG)) {
            type = ImageType.JPEG;
        } else if (ImageType.JPEG_WITH_PREFIX.equals(contentType) &&
                name.toLowerCase().endsWith(ImageType.JPEG)) {
            type = ImageType.JPEG;
        }
        return type;
    }


    @ApiOperation("删除 - 上传的图片")
    @DeleteMapping("/delete")
    public Result remove(@RequestParam(value = "fileUrl") String fileUrl) {
        return AliyunUtil.delete(fileUrl, properties.getAliyun());
    }
}
