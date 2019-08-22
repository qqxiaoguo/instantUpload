package com.lpjj.application.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * com.lpjj.application.entity
 * 黄新乐
 * 2019/7/18
 */
@Setter
@Getter
@ToString
@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 编码后的图片
     */
    private String base64_image;
    /**
     * 当前时间
     */
    private Date time;
    /**
     * 设备ID
     */
    @Column(name = "device_id")
    private String device_id;
}
