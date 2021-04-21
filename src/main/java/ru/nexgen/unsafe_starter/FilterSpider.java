package ru.nexgen.unsafe_starter;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
@Component("findBy")
public class FilterSpider implements TransformationSpider {
    private Map<String, FilterTransformation> filterTransformationMap;

    @Override
    public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames) {
        String fieldName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, methodWords);
        String filterName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(filterTransformationMap.keySet(), methodWords);
        return filterTransformationMap.get(filterName);

    }
}
