package com.jili20.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.jili20.result.Result;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 上传图片工具类
 *
 * @author bing_  @create 2022/1/21-16:49
 */
public final class AliyunUtil {

    /**
     * 上传图片文件
     *
     * @param file   MultipartFile 文件对象
     * @param aliyun AliyunProperties 阿里云配置
     * @return
     */
    public static Result uploadFileToOss(String type, MultipartFile file, AliyunProperties aliyun) {

        // 上传
        //构建日期路径
        String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
        // 上传文件所在目录名，当天上传的文件放到当天日期的目录下。article/19990101/123123.png
        String folderName = type.toLowerCase() + timeFolder;
        // 保存到 OSS 中的文件名，采用 UUID 命名。把 UUID 里的 - 转换为 空
        String fileName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中，获取文件扩展名（截取文件名后缀，从最后一个 . 开始，之后的都截取出来，如 .png  .jpeg
        String fileExtensionName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 文件在 OSS 中存储的完整路径
        String fileUrl = folderName + fileName + fileExtensionName;
        OSS ossClient = null;
        try {
            // 获取 OSS 客户端实例
            ossClient = new OSSClientBuilder().build(aliyun.getEndpoint(), aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());
            // 上传文件到OSS 并响应结果
            PutObjectResult putObjectResult = ossClient.putObject(aliyun.getBucketName(), fileUrl, file.getInputStream());
            ResponseMessage response = putObjectResult.getResponse();
            if (response == null) {
                // 上传成功
                // 返回上传文件的访问完整路径
                return Result.ok().data("fileUrl", aliyun.getBucketDomain() + fileUrl);
            } else {
                // 上传失败，OOS服务端会响应状态码和错误信息
                String errorMsg = "响应的错误状态码是【" + response.getStatusCode() + "】，" +
                        "错误信息【" + response.getErrorResponseAsString() + "】";
                return Result.error().message(errorMsg);
            }
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        } finally {
            if (ossClient != null) {
                // 关闭 OSSClient。
                ossClient.shutdown();
            }
        }
    }

    /**
     * 根据文件url删除
     *
     * @param fileUrl
     */
    public static Result delete(String fileUrl, AliyunProperties aliyun) {
        // 去除文件 url 中的 Bucket域名 article/20200729/9d83082760e84c15a685d6e61338174a.png
        String filePath = fileUrl.replace(aliyun.getBucketDomain(), "");
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(aliyun.getEndpoint(), aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());
            // 删除
            ossClient.deleteObject(aliyun.getBucketName(), filePath);
            return Result.ok();
        } catch (Exception e) {
            return Result.error().message(e.getMessage()).message("删除图片失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
