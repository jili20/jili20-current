package com.jili20.exception;

import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理类
 *
 * @author bing_  @create 2022/1/6-20:35
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    //@ExceptionHandler(value = Exception.class)
    //public Result handleException(Exception e) {
    //    log.error(e.getMessage(), e);
    //    return Result.fail();
    //}

    @ExceptionHandler(value = ExpiredJwtException.class)
    public Result handleException(ExpiredJwtException e) {
        log.error(e.getMessage(), e);
        // 您的身份已过期，请重新登录
        return Result.setResult(ResponseEnum.NEED_LOGIN_AGAIN);
    }

    @ExceptionHandler(value = InsufficientAuthenticationException.class)
    public Result handleException(InsufficientAuthenticationException e) {
        log.error(e.getMessage(), e);
        // 用户登录，用户名错误，抛出此异常(认证失败，请查询登录)
        return Result.setResult(ResponseEnum.LOGIN_ERROR);
    }


    @ExceptionHandler(value = BadCredentialsException.class)
    public Result handleException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        // 用户登录，密码错误抛出这个异常(因在自定义处理器中捕捉不到这个异常，所以在这里定义处理)
        return Result.setResult(ResponseEnum.PASSWORD_ERROR);
    }

    @ExceptionHandler(value = SizeLimitExceededException.class)
    public Result handleException(SizeLimitExceededException e) {
        log.error(e.getMessage(), e);
        // 上传文件（请求参数长度已超出最大范围）
        return Result.setResult(ResponseEnum.REQUEST_PARAMETER_LENGTH_ERROR);
    }


    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result handleException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        // 请求参数格式错误
        return Result.setResult(ResponseEnum.REQUEST_TYPE_ERROR);
    }


    @ExceptionHandler(value = BadSqlGrammarException.class)
    public Result handleException(BadSqlGrammarException e) {
        log.error(e.getMessage(), e);
        // sql 语法错误
        return Result.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }


    @ExceptionHandler(value = jili20Exception.class)
    public Result handleException(jili20Exception e) {
        log.error(e.getMessage(), e);
        return Result.error().message(e.getMessage()).code(e.getCode());
    }

    /**
     * Controller上一层相关异常
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public Result handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        // SERVLET_ERROR(-102, "servlet 请求异常"),
        return Result.error().message(ResponseEnum.SERVLET_ERROR.getMessage()).code(ResponseEnum.SERVLET_ERROR.getCode());
    }
}
