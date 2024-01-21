package com.mju.mentoring.exam.number_bean;

import org.springframework.stereotype.Component;

//@Component
public class RandomNumberGenerator implements NumberGenerator {

    @Override
    public int generate() {
        return (int) (Math.random() * 10);
    }
}
