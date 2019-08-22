
package com.lpjj.application.entity.faceDB;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Results {

    private int classify;
    private String comment;
    private int create_time;
    private int creator_id;
    private int encrypt;
    private int face_image_num;
    private int failed_picture_num;
    private int id;
    private String name;
    private int person_type;
    private int register_type;
    private int total_picture_num;
    private int type;

}