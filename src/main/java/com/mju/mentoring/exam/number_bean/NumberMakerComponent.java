package com.mju.mentoring.exam.number_bean;

import org.springframework.stereotype.Component;

@Component
public class NumberMakerComponent {

    private final NumberGenerator numberGenerator;

    public NumberMakerComponent(final NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public boolean isHigherThanFive() {
        int number = numberGenerator.generate();

        return number > 5;
    }

    public boolean isHigherThanOneHundred() {
        int number = numberGenerator.generate();

        return number > 100;
    }
}
