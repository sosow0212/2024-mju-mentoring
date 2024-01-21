package com.mju.mentoring.exam.number_bean;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class OverHundredNumberGenerator implements NumberGenerator {

    @Override
    public int generate() {
        return 101;
    }
}