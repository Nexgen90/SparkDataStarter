package ru.nexgen.SparkDataStarter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public interface Finalizer {
    Object doAction(Dataset<Row> dataset);
}
