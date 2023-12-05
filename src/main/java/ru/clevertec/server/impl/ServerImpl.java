package ru.clevertec.server.impl;

import ru.clevertec.entity.Request;
import ru.clevertec.entity.Response;
import ru.clevertec.server.Server;
import ru.clevertec.util.SleepUtil;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Имплементация интерфейса для сервера
 */
public class ServerImpl implements Server {

    private final Lock lock;
    private final ExecutorService executor;

    public ServerImpl(int nThreads) {
        this.lock = new ReentrantLock();
        this.executor = Executors.newFixedThreadPool(nThreads);
    }

    /**
     * Обработка запросов от клиента
     *
     * @param requests запросы от клиента List<Request>
     * @return ответ от сервера Future<Response>
     */
    @Override
    public Future<Response> processingRequest(List<Request> requests) {
        try {
            lock.lock();
            SleepUtil.sleep(100, 1000);

            Callable<Response> callable = () -> new Response(requests.size());
            return executor.submit(callable);
        } finally {
            executor.shutdown();
            lock.unlock();
        }
    }
}
