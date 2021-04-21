package ru.nexgen.starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
public class CountFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset) {
        return dataset.count();
    }
}
