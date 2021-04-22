package ru.nexgen.unsafe_starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * Для реализации различных фильтров, сортировок, map ...
 *
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public interface SparkTransformation {
    Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args);
}
