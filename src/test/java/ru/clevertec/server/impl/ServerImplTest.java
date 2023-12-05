package ru.clevertec.server.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.entity.Request;
import ru.clevertec.entity.Response;
import ru.clevertec.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для {@link ServerImpl}
 */
class ServerImplTest {

    private final Server server = new ServerImpl(1);

    @Nested
    class ProcessingRequestTest {
        @SneakyThrows
        @Test
        void checkProcessingRequestShouldReturnCorrectRequest() {
            // given
            List<Request> requests = new ArrayList<>(
                    IntStream.rangeClosed(1, 100).boxed().map(Request::new).toList()
            );
            Response expectedResponse = new Response(100);

            // when
            Response actualResponse = server.processingRequest(requests).get();

            // than
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void checkProcessingRequestShouldSleepCorrectTime() {
            // given
            List<Request> requests = new ArrayList<>(
                    IntStream.rangeClosed(1, 10).boxed().map(Request::new).toList()
            );
            long minTime = 100L;
            long maxTime = 1000L;

            // when
            long startTime = System.currentTimeMillis();
            server.processingRequest(requests);
            long endTime = System.currentTimeMillis();

            long actualElapsedTime = endTime - startTime;

            // than
            assertAll(
                    () -> assertTrue(actualElapsedTime >= minTime),
                    () -> assertTrue(actualElapsedTime <= maxTime)
            );
        }
    }
}
