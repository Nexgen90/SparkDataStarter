package ru.nexgen.unsafe_starter.strategy.transformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import ru.nexgen.unsafe_starter.utils.OrderedBag;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
public class SortTransformation implements SparkTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args) {
        return dataset.orderBy(columnNames.get(0), columnNames.stream().skip(1).toArray(String[]::new));
    }
}
