package com.mju.mentoring.exam.number_bean;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("over100")
public class OverHundredNumberGenerator implements NumberGenerator {

    @Override
    public int generate() {
        return 1000;
    }
}
