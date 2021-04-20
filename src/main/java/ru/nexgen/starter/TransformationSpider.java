package ru.nexgen.starter;

import java.lang.reflect.Method;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public interface TransformationSpider {
    SparkTransformation getTransformation(Method[] methods);
}
