package ru.nexgen.unsafe_starter;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
@Component("orderBy")
public class OrderTransformationSpider implements TransformationSpider {
    @Override
    public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames) {

        return null;
    }
}
