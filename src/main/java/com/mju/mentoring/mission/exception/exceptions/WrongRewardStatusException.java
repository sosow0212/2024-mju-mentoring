package com.mju.mentoring.mission.exception.exceptions;

public class WrongRewardStatusException extends RuntimeException{

    public WrongRewardStatusException() {
        super("잘못된 reward status입니다!");
    }
}
