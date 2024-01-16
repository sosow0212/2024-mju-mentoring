package com.mju.mentoring.exam.number_pojo;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class test {

    @Test
    void 숫자가_5보다_크면_true를_반환한다 () {
        // given
        NumberMaker numberMaker = new NumberMaker(new FakeGenerator());

        // when
        boolean result = numberMaker.isHigherThanFive();

        // then
        assertThat(result).isTrue();
     }
}
