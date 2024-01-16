package com.mju.mentoring.exam.number_pojo;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class NumberMakerTest {

    /**
     * 테스트 코드는 건들이지 말고
     * 주석친 부분만 고쳐서 테스트를 통과시켜주세요.
     * 테스트 성공을 위해서 구현체를 만들어봅시다!
     */

    @Test
    void 숫자가_5보다_큰_경우_true_를_반환한다() {
        // given
        NumberMaker numberMaker = null;
        // numberMaker = new NumberMaker(XXX) -> 주석 풀고 구현하기!

        // when
        boolean result = numberMaker.isHigherThanFive();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 숫자가_5보다_작은_경우_false_를_반환한다() {
        // given
        NumberMaker numberMaker = null;
        // numberMaker = new NumberMaker(XXX) -> 주석 풀고 구현하기!

        // when
        boolean result = numberMaker.isHigherThanFive();

        // then
        assertThat(result).isFalse();
    }
}
