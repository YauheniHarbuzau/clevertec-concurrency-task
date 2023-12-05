package ru.clevertec;

import ru.clevertec.client.Client;
import ru.clevertec.client.impl.ClientImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Main-класс - точка входа в приложение
 */
public class Main {

    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
        Client client = new ClientImpl(dataList, 2);
        System.out.println(client.accumulate());
    }
}
