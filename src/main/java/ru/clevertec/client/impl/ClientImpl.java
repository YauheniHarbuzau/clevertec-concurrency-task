package ru.clevertec.client.impl;

import lombok.Getter;
import ru.clevertec.client.Client;
import ru.clevertec.entity.Request;
import ru.clevertec.server.Server;
import ru.clevertec.server.impl.ServerImpl;
import ru.clevertec.util.SleepUtil;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Имплементация интерфейса для клиента
 */
public class ClientImpl implements Client {

    @Getter
    private final List<Integer> dataList;

    private final Server server;
    private final Lock lock;

    public ClientImpl(List<Integer> dataList, int nThreads) {
        this.dataList = dataList;
        this.server = new ServerImpl(nThreads);
        this.lock = new ReentrantLock();
    }

    /**
     * Подготовка и отправление запросов от клиента
     *
     * @return запросы от клиента List<Request>
     */
    @Override
    public List<Request> sendRequests() {
        return dataList != null && !dataList.isEmpty() ?

                IntStream.range(0, dataList.size())
                        .parallel()
                        .mapToObj(i -> new Request(getValue()))
                        .toList() :

                Collections.emptyList();
    }

    /**
     * Обработка значения, полученного от сервера
     *
     * @return аккумулированное значение AtomicInteger
     */
    @Override
    public AtomicInteger accumulate() {
        try {
            var response = server.processingRequest(sendRequests()).get().dataListSize();
            var accumulator = new AtomicInteger(0);

            accumulator.updateAndGet(i -> (response + 1) * response / 2);
            return accumulator;
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Получение значения из списка данных {@link ClientImpl#dataList}
     *
     * @return значение Integer
     */
    private Integer getValue() {
        try {
            lock.lock();
            SleepUtil.sleep(100, 500);

            var idx = new Random().nextInt(dataList.size());
            var value = dataList.get(idx);

            dataList.remove(idx);
            return value;
        } finally {
            lock.unlock();
        }
    }
}
