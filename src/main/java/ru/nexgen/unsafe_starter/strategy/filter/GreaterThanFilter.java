package ru.nexgen.unsafe_starter.strategy.filter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;
import ru.nexgen.unsafe_starter.utils.OrderedBag;
import ru.nexgen.unsafe_starter.strategy.transformation.FilterTransformation;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
@Component("greaterThan")
public class GreaterThanFilter implements FilterTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args) {
        return dataset.filter(functions.col(columnNames.get(0)).geq(args.takeAndRemove()));
    }
}
