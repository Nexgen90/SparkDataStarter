package ru.nexgen.unsafe_starter;

import java.util.List;
import java.util.Set;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public interface TransformationSpider {
    SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames);
}
