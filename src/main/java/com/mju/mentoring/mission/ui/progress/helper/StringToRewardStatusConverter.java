package com.mju.mentoring.mission.ui.progress.helper;

import com.mju.mentoring.mission.domain.progress.RewardStatus;
import com.mju.mentoring.mission.exception.exceptions.WrongRewardStatusException;
import org.springframework.core.convert.converter.Converter;

public class StringToRewardStatusConverter implements Converter<String, RewardStatus> {

    @Override
    public RewardStatus convert(final String source) {
        try {
            return RewardStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new WrongRewardStatusException();
        }
    }
}
