package ru.tinkoff.edu.java.test.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.edu.java.parser.StackOverflowParser;
import ru.tinkoff.edu.java.parser.response.BaseResponse;
import ru.tinkoff.edu.java.parser.response.StackOverflowResponse;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StackOverflowParserTest {
    StackOverflowParser stackOverflowParser = new StackOverflowParser();

    @ParameterizedTest
    @MethodSource("provideValidLinks")
    void parse_returnStackOverflowResponse_ValidURLs(String url, String expectedQuestionId) {
        // given

        // when
        Optional<BaseResponse> response = stackOverflowParser.parse(url);

        // then
        assertThat(response).isNotEmpty();
        assertThat(response.get()).isInstanceOf(StackOverflowResponse.class);

        var sofResponse = (StackOverflowResponse) response.get();
        assertAll("Validate question id in response",
                () -> assertThat(sofResponse.questionId()).isEqualTo(expectedQuestionId),
                () -> assertThat(sofResponse.questionId()).isEqualTo(expectedQuestionId));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLinks")
    void parse_returnEmptyOptional_InvalidURLs(String url) {
        // given

        // when
        Optional<BaseResponse> response = stackOverflowParser.parse(url);

        // then
        assertThat(response).isEmpty();
    }

    static Stream<Arguments> provideValidLinks() {
        return TestSamples.provideStackOverflowValidLinks();
    }

    static Stream<String> provideInvalidLinks() {
        return Stream.of(
                "http://github.com/emil/project",
                "https://stackoverflow.com/questions/",
                "https://stackoverflow.com/questions/      ",
                "https://stackoverflow.com/questions/aaaa",
                "https://stackoverflow.com/questions/123 123",
                "https://vk.com/user/repo"
        );
    }


}
