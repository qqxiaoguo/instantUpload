
package com.lpjj.application.entity.retrieval;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class Retrieval {

    private String face_image_id_str;
    private List<Integer> repository_ids;
    private int threshold;


}