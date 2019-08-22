package com.lpjj.application.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
public class ExceptionUtil {
 

 
    private ExceptionUtil() {
    }
 
    /**
     * 获取异常信息
     *
     * @param e 异常
     */
    public static String getErrorMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    log.info(e1.getMessage());
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
    /**
     * 异常信息-->json
     *
     */
    public static String resultOf(ResultStatusCode resultStatusCode) {
        SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(ResultStatusCode.class);
        return JSON.toJSONString(resultStatusCode, config);
    }
 
}