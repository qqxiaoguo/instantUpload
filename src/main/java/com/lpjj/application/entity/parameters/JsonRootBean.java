
package com.lpjj.application.entity.parameters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "result")
public class JsonRootBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    private String results;

    private long retrieval_query_id;

    private int rtn;

    private int total;

    @Column(name = "image_base64")
    private String image_base64;

    @Column(name = "device_id")
    private String device_id;

    private Date time;
}