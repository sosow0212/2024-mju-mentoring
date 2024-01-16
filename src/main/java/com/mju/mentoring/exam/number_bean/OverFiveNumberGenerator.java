package com.mju.mentoring.exam.number_bean;

import org.springframework.stereotype.Component;

//@Component
public class OverFiveNumberGenerator implements NumberGenerator {

    private final int TEN = 10;

    @Override
    public int generate() {
        return TEN;
    }
}
