package ru.nexgen.unsafe_starter.strategy.transformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;
import ru.nexgen.unsafe_starter.utils.OrderedBag;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
@Component("between")
public class BetweenTransformation implements FilterTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args) {
        return dataset.filter(functions.col(columnNames.get(0)).between(args.takeAndRemove(), args.takeAndRemove()));
    }
}
