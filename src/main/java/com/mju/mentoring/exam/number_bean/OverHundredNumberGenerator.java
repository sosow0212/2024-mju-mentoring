package com.mju.mentoring.exam.number_bean;

import org.springframework.stereotype.Component;

@Component
public class OverHundredNumberGenerator implements NumberGenerator {

    private static final int OVER_HUNDRED_NUMBER = 101;

    @Override
    public int generate() {
        return OVER_HUNDRED_NUMBER;
    }
}
