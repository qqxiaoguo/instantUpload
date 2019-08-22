package com.lpjj.application.entity.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * com.lpjj.application.entity.result
 * 黄新乐
 * 2019/7/18
 */
@Getter
@Setter
@ToString
public class Result {
    private Integer code;

    private List<IdentifyResults> data;

    private String message;

    public Result() {
    }

    public Result(Integer code, List<IdentifyResults> data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
