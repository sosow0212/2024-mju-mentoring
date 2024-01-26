package com.mju.mentoring.exam.number_pojo;

public class NumberMaker {

    private static final int BOUNDARY = 5;

    private final NumberGenerator numberGenerator;

    public NumberMaker(final NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public boolean isHigherThanFive() {
        int number = numberGenerator.generate();

        return number > BOUNDARY;
    }
}
