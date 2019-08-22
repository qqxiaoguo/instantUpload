/**
 * Copyright 2019 bejson.com
 */
package com.lpjj.application.entity.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class Login {

    private String device_type;
    private Extra extra;
    private String message;
    private int rtn;
    private long session_id;



}