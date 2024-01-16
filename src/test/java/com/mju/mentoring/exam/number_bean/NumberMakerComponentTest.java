package com.mju.mentoring.exam.number_bean;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class NumberMakerComponentTest {

    @Autowired
    private NumberMakerComponent numberMakerComponent;

    /**
     * 해당 테스트 클래스는 수정하지 마시고, 작동을 통해 결과만 검증합니다!
     * exam/number_bean 디렉토리에
     * 새로운 클래스를 추가한 후 의존성을 주입을 시켜서 아래 테스트를 통과해주세요.
     */

    @Test
    void 숫자가_100보다_큰_경우_true_를_반환한다() {
        // when
        boolean result = numberMakerComponent.isHigherThanOneHundred();

        // then
        assertThat(result).isTrue();
    }
}
