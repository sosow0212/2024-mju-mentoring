package com.mju.mentoring.exam.number_bean;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
//@SpringBootTest // 통합테스트
class NumberMakerComponentTest2 {

//    @Autowired
    private NumberMakerComponent numberMakerComponent;

//    @Test
    void 숫자는_5이상으로_반환된다() {
        // given

        // when
        boolean result = numberMakerComponent.isHigherThanFive();

        // then
        assertThat(result).isTrue();
    }
}
