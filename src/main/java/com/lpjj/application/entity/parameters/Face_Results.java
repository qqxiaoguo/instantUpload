
package com.lpjj.application.entity.parameters;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class Face_Results {

    private Date birthday;
    private int born_year;
    private int camera_id;
    private String city;
    private String external_id;
    private long face_image_id;
    private String face_image_id_str;
    private String face_image_uri;
    private int gender;
    private String name;
    private String person_id;
    private String picture_uri;
    private String province;
    private int rec_gender;
    private int repository_id;
    private int similarity;
    private int timestamp;

}
