package com.lpjj.application.exception;

import com.alibaba.fastjson.JSON;
import com.lpjj.application.entity.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.zip.DataFormatException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * http请求的方法不正确
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public String httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("http请求的方法不正确:【" + e.getMessage() + "】");
        return ExceptionUtil.resultOf(ResultStatusCode.RequestMethodNotAllowed);
    }

    /**
     * 请求参数不全
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public String missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("请求参数不全:【" + e.getMessage() + "】");
        return ExceptionUtil.resultOf(ResultStatusCode.MissingServletRequestParameter);
    }

    /**
     * 请求参数类型不正确
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public String typeMismatchExceptionHandler(TypeMismatchException e) {
        log.error("请求参数类型不正确:【" + e.getMessage() + "】");
        return ExceptionUtil.resultOf(ResultStatusCode.TypeMismatchException);
    }

    /**
     * 数据格式不正确
     */
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public String dataFormatExceptionHandler(DataFormatException e) {
        log.error("数据格式不正确:【" + e.getMessage() + "】");
        return ExceptionUtil.resultOf(ResultStatusCode.DataFormatException);
    }

    /**
     * 用户没找到
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public String nullPointerExceptionHandler(NullPointerException e) {
        log.error("图片比对失败:【" + e.getMessage() + "】");
        return JSON.toJSONString(new Result(-1, null, "To deal with failure"));
    }

    /**
     * 非法输入
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("非法输入:【" + e.getMessage() + "】");
        return ExceptionUtil.resultOf(ResultStatusCode.IllegalArgumentException);
    }


    @ExceptionHandler  //处理其他异常
    @ResponseBody
    public String allExceptionHandler(Exception e) {
        log.error("具体错误信息:【" + ExceptionUtil.getErrorMessage(e) + "】"); //会记录出错的代码行等具体信息
        int count = 0; //只打印15行的错误堆栈
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            log.error(stackTraceElement.toString());
            if (count++ > 13) break;
        }
        return ExceptionUtil.resultOf(ResultStatusCode.SystemException);
    }

}
