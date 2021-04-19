package ru.nexgen.SparkDataStarter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public class JsonDataExtractor implements DataExtractor {
    @Override
    public Dataset<Row> load(String pathToData, ConfigurableApplicationContext context) {
        return context.getBean(SparkSession.class).read().json(pathToData);
    }
}
