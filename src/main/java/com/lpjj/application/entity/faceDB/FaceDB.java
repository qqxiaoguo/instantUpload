
package com.lpjj.application.entity.faceDB;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class FaceDB {

    private String message;
    private List<Results> results;
    private int rtn;

}