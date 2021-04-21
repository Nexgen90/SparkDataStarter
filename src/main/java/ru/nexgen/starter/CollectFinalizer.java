package ru.nexgen.starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
public class CollectFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, Class<?> model) {
        Encoder<?> encoder = Encoders.bean(model);
        return dataset.as(encoder).collectAsList();
    }
}
