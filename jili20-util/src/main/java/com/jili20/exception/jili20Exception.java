package com.jili20.exception;

import com.jili20.result.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义全局异常类
 *
 * @author bing_  @create 2022/1/6-20:34
 */
@Data
@NoArgsConstructor
public class jili20Exception extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;

    /**
     *
     * @param message 错误消息
     */
    public jili20Exception(String message) {
        this.message = message;
    }

    /**
     *
     * @param message 错误消息
     * @param code 错误码
     */
    public jili20Exception(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    /**
     *
     * @param message 错误消息
     * @param code 错误码
     * @param cause 原始异常对象
     */
    public jili20Exception(String message, Integer code, Throwable cause) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    /**
     *
     * @param responseEnum 接收枚举类型
     */
    public jili20Exception(ResponseEnum responseEnum) {
        this.message = responseEnum.getMessage();
        this.code = responseEnum.getCode();
    }

    /**
     *
     * @param responseEnum 接收枚举类型
     * @param cause 原始异常对象
     */
    public jili20Exception(ResponseEnum responseEnum, Throwable cause) {
        super(cause);
        this.message = responseEnum.getMessage();
        this.code = responseEnum.getCode();
    }

}
