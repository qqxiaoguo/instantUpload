
package com.lpjj.application.entity.picturesync;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class Picturesync {

    private String message;
    private String picture_uri;
    private List<Results> results;
    private int rtn;
    private String session_id;

}