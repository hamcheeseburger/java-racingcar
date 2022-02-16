package racingcar.view;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.domain.RacingGame;

class InputViewTest {

    @Test
    @DisplayName("시도횟수가 문자열이 아닌지 검증한다.")
    void checkTryCountIsDigit() {
        InputStream customInputStream = new ByteArrayInputStream("123a".getBytes());
        System.setIn(customInputStream);

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> InputView.inputTryCount())
            .withMessageMatching("시도횟수는 숫자이어야 합니다.");
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("자동차 입력 값이 존재하지 않으면 예외를 발생한다.")
    void checkCarNamesNullOrEmpty(String input) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Validator.validateCarNames(input))
            .withMessageMatching("자동차 입력 값이 존재해야한다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1"})
    @DisplayName("입력한 시도횟수가 0이하이면 예외가 발생한다.")
    void negativeCount(String tryCountText) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Validator.validateTryCount(tryCountText))
            .withMessageMatching("시도횟수는 0이하의 값이 들어올 수 없다.");
    }
}