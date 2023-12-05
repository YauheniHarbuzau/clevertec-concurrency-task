package ru.clevertec.client;

import ru.clevertec.entity.Request;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Интерфейс для клиента
 */
public interface Client {

    List<Request> sendRequests();

    AtomicInteger accumulate();
}
