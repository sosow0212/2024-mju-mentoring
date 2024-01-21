package com.mju.mentoring.exam.number_pojo;

public class UnderFiveNumberGenerator implements NumberGenerator {

    private static final int UNDER_FIVE_NUMBER = 4;

    @Override
    public int generate() {
        return UNDER_FIVE_NUMBER;
    }
}
