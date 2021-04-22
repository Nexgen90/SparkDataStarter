package ru.nexgen.unsafe_starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
@Component("collect")
public class CollectFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, Class<?> model) {
        Encoder<?> encoder = Encoders.bean(model);
        return dataset.as(encoder).collectAsList();
    }
}