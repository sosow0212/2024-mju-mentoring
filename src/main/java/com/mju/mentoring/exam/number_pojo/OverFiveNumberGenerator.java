package com.mju.mentoring.exam.number_pojo;

public class OverFiveNumberGenerator implements NumberGenerator {

    private static final int OVER_FIVE_NUMBER = 6;

    @Override
    public int generate() {
        return OVER_FIVE_NUMBER;
    }
}
