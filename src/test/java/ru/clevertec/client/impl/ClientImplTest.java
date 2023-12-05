package ru.clevertec.client.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.client.Client;
import ru.clevertec.entity.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для {@link ClientImpl}
 */
class ClientImplTest {

    @Nested
    class SendRequestsTest {
        @Test
        void checkSendRequestsShouldReturnListWithCorrectSize() {
            // given
            List<Integer> dataList = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
            Client client = new ClientImpl(dataList, 1);
            int expectedRequestsSize = 100;

            // when
            int actualRequestsSize = client.sendRequests().size();

            // than
            assertEquals(expectedRequestsSize, actualRequestsSize);
        }

        @ParameterizedTest
        @MethodSource("provideValues")
        void checkSendRequestsShouldReturnRequestsWithCorrectValues(Integer argument) {
            // given
            List<Integer> dataList = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
            Client client = new ClientImpl(dataList, 1);

            // when
            List<Request> actualRequests = client.sendRequests();
            List<Integer> actualRequestsValues = actualRequests.stream().map(Request::value).toList();

            // than
            assertThat(actualRequestsValues).contains(argument);
        }

        private static List<Integer> provideValues() {
            return IntStream.rangeClosed(1, 100).boxed().toList();
        }

        @Test
        void checkSendRequestsShouldReturnEmptyList() {
            // given
            List<Integer> dataList = new ArrayList<>(0);
            Client client = new ClientImpl(dataList, 1);
            int expectedRequestsSize = 0;

            // when
            List<Request> actualRequests = client.sendRequests();
            int actualRequestsSize = actualRequests.size();

            // than
            assertEquals(expectedRequestsSize, actualRequestsSize);
        }

        @Test
        void checkSendRequestsShouldRemoveValueFromDataList() {
            // given
            List<Integer> dataList = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
            ClientImpl client = new ClientImpl(dataList, 1);
            int expectedDataListSize = 0;

            // when
            client.sendRequests();
            List<Integer> actualDataList = client.getDataList();
            int actualDataListSize = actualDataList.size();

            // than
            assertEquals(expectedDataListSize, actualDataListSize);
        }
    }
}
