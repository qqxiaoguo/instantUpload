package com.lpjj.application.xin;

import org.junit.Test;
import org.springframework.stereotype.Component;

@Component
public class PinCutTest implements  PinService {

    @Log
    @Test
    public void  test() {

        System.out.println("我是执行体");
    }
}
