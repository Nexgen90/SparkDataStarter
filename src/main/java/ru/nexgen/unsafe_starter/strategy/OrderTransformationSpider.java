package ru.nexgen.unsafe_starter.strategy;

import org.springframework.stereotype.Component;
import ru.nexgen.unsafe_starter.strategy.transformation.SortTransformation;
import ru.nexgen.unsafe_starter.strategy.transformation.SparkTransformation;
import ru.nexgen.unsafe_starter.utils.WordsMatcher;
import scala.Tuple2;

import java.util.ArrayList;
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
        String sortColumn = WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, methodWords);

        List<String> additional = new ArrayList<>();
        //но может быть КолонокаAndЕщйодна
        while (!methodWords.isEmpty() && methodWords.get(0).equalsIgnoreCase("And")) {
            methodWords.remove(0);
            additional.add(WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, methodWords));
        }

        additional.add(0, sortColumn);
        return new Tuple2<>(new SortTransformation(), additional);
    }
}
