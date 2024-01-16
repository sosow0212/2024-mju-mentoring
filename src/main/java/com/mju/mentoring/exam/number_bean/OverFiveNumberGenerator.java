package com.mju.mentoring.exam.number_bean;

import org.springframework.stereotype.Component;

//@Component
public class OverFiveNumberGenerator implements NumberGenerator {

    @Override
    public int generate() {
        return 10;
    }
}
