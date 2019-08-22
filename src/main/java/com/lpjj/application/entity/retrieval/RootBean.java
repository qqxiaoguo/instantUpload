/**
 * Copyright 2019 bejson.com
 */
package com.lpjj.application.entity.retrieval;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class RootBean {

    private Condition condition;
    private Retrieval retrieval;
    private List<String> fields;
    private Order order;
    private int start = 0;
    private int limit = 1;

    public RootBean() {
        this.fields = new ArrayList<>();
        fields.add("camera_id");
        fields.add("face_image-uri");
        fields.add("picture-uri");
        fields.add("timestamp");
        fields.add("similarity");
        fields.add("face_rect");
    }
}