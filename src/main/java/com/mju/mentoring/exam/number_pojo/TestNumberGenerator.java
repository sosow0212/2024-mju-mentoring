package com.mju.mentoring.exam.number_pojo;

public class TestNumberGenerator implements NumberGenerator{

    private final int testNumber;
    public TestNumberGenerator(int testNumber){
        this.testNumber = testNumber;
    }
    @Override
    public int generate() {
        return testNumber;
    }
}
