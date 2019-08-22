package com.lpjj.application.entity.FDS_faceDB;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * com.lpjj.application.entity.FDS_faceDB
 * 黄新乐
 * 2019/8/13
 * 添加比对库的对象
 */
@Data
@Builder
public class FDS_Facedb {

    /**
     * 库名。库名不能与已有的库名冲突(最长128字符)(必填)
     */
    private String name;
    /**
     * 类型，0全部保存在一台机器的库(路人库)，1可以分布式存储的库(静态库)，默认为0（非必填）
     */

    private int type=1;
    /**
     * 不用关注
     */

    private int encrypt=0;
    /**
     * 仓库备注(最长128字符)（非必填）
     */
    private String comment;
    /**
     * 库类型 0： 表示人脸 1：表示车牌
     */
    private int classify=0;
    /**
     * 所属组id（非必填，默认为1（最顶层组））
     */
    private int person_type;
}
