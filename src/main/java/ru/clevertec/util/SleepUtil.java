package ru.clevertec.util;

import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Утилитарный класс для предоставления случайной задержки в заданном диапазоне миллисекунд
 */
@UtilityClass
public class SleepUtil {

    public void sleep(int minValue, int maxValue) {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(minValue, maxValue + 1));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
