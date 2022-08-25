package com.jili20.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果
 * @author bing_  @create 2022/2/1-9:50
 * JsonInclude(JsonInclude.Include.NON_NULL) 实体类与json互转的时候，属性值为null的不参与序列化
 */
@Data
@Slf4j
@Accessors(chain = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {

    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    /**
     * 构造函数私有化，外面不能 new
     */
    private Result() {
    }

    /**
     * 返回成功结果
     */
    public static Result ok() {
        Result r = new Result();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 返回失败结果
     */
    public static Result error() {
        Result r = new Result();
        r.setCode(ResponseEnum.FAIL.getCode());
        r.setMessage(ResponseEnum.FAIL.getMessage());
        return r;
    }

    /**
     * 设置特定的响应结果
     * @param ResponseEnum
     * @return
     */
    public static Result setResult(ResponseEnum ResponseEnum) {
        Result r = new Result();
        r.setCode(ResponseEnum.getCode());
        r.setMessage(ResponseEnum.getMessage());
        return r;
    }

    /**
     * 返回 key：对象
     */
    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 返回 map
     */
    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 设置特定的响应消息
     * @param message
     * @return
     */
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 设置特定的响应状态码
     * @param code
     * @return
     */
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }


    /**
     * 设置特定的响应状态码和返回信息
     * @param code
     * @param message
     * @return
     */
    public static Result build(int code, String message) {
        log.debug("返回结果：code={}, message={}", code, message);
        return new Result(code, message, null);
    }
}









