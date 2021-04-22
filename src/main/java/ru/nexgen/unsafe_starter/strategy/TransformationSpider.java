package ru.nexgen.unsafe_starter.strategy;

import ru.nexgen.unsafe_starter.strategy.transformation.SparkTransformation;
import scala.Tuple2;

import java.util.List;
import java.util.Set;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public interface TransformationSpider {
    Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames);
}
