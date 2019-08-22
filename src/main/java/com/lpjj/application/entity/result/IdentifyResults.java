package com.lpjj.application.entity.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * com.lpjj.application.entity.result
 * 黄新乐
 * 2019/7/18
 * 识别结果封装
 */
@Getter
@Setter
@ToString
public class IdentifyResults {

    private String face_src;

    private String face_reg;

    private long score;

    private String face_name;

}
