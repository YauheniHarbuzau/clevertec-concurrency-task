package ru.clevertec.integration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.client.Client;
import ru.clevertec.client.impl.ClientImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Интеграционный тест для {@link Client#accumulate()}
 */
public class IntegrationTest {

    @Nested
    class ClientAccumulateTest {
        @Test
        void checkClientAccumulateShouldReturnCorrectResult() {
            // given
            List<Integer> dataList = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
            Client client = new ClientImpl(dataList, 1);
            Integer expectedResult = 5050;

            // when
            Integer actualResult = client.accumulate().get();

            // than
            assertEquals(expectedResult, actualResult);
        }

        @Test
        void checkClientAccumulateShouldReturnZero() {
            // given
            List<Integer> dataList = new ArrayList<>(0);
            Client client = new ClientImpl(dataList, 1);
            Integer expectedResult = 0;

            // when
            Integer actualResult = client.accumulate().get();

            // than
            assertEquals(expectedResult, actualResult);
        }
    }
}
