package ru.nexgen.unsafe_starter.strategy;

import org.springframework.stereotype.Component;
import ru.nexgen.unsafe_starter.strategy.transformation.SparkTransformation;
import scala.Tuple2;

import java.util.List;
import java.util.Set;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
@Component("orderBy")
public class OrderTransformationSpider implements TransformationSpider {
    @Override
    public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames) {
//        WordsMatcher.findAndRemoveMatchingPiecesIfExists()
//        return new Tuple2<>(new SortTransformation(), );

        return null;
    }
}
