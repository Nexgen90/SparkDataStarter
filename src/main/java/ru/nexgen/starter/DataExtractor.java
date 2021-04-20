package ru.nexgen.starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public interface DataExtractor {
    /**
     *
     * @param pathToData
     * @param context именно спринговый контекст, а не JavaSparkContext & SparkSession,
     *                потому что spark часто деприкейтит такие классы
     * @return
     */
    Dataset<Row> load(String pathToData, ConfigurableApplicationContext context);
}
