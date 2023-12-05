package ru.clevertec.server;

import ru.clevertec.entity.Request;
import ru.clevertec.entity.Response;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Интерфейс для сервера
 */
public interface Server {

    Future<Response> processingRequest(List<Request> requests);
}
