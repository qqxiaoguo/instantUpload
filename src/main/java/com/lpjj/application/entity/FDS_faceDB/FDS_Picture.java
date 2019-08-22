package com.lpjj.application.entity.FDS_faceDB;

import lombok.Builder;
import lombok.Data;

/**
 * com.lpjj.application.entity.FDS_faceDB
 * 黄新乐
 * 2019/8/13
 * 向比对库中添加一个人脸图
 */
@Data
@Builder
public class FDS_Picture {
    /**
     * 图片所在库的id, 必填项
     */
    private int  repository_id;
    /**
     * picture_image_content_base64
     */
    private String picture_image_content_base64;
    /**
     * person_id
     */
    private String person_id;
    /**
     * name
     */
    private String name;
    /**
     * birthday
     */
    private String birthday;
    /**
     * gender
     */
    private int gender;
}
