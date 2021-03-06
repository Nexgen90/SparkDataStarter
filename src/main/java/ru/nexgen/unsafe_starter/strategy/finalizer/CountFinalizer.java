package ru.nexgen.unsafe_starter.strategy.finalizer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;
import ru.nexgen.unsafe_starter.strategy.finalizer.Finalizer;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
@Component("count")
public class CountFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, Class<?> model) {
        return dataset.count();
    }
}
