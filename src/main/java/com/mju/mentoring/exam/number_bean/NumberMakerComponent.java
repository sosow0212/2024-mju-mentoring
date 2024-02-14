package com.mju.mentoring.exam.number_bean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class NumberMakerComponent {

    private final NumberGenerator numberGenerator;

    public NumberMakerComponent(@Qualifier("over100") final NumberGenerator numberGenerator) {
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
