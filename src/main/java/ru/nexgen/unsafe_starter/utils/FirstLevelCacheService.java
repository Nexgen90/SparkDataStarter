package ru.nexgen.unsafe_starter.utils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.nexgen.unsafe_starter.DataExtractorResolver;
import ru.nexgen.unsafe_starter.dataextractor.DataExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
public class FirstLevelCacheService {

    private Map<Class<?>, Dataset<Row>> modelToDataset = new HashMap<>();

    @Autowired
    private DataExtractorResolver extractorResolver;

    public List readDataFor(long ownerId, Class<?> modelClass, String pathToSource, String foreignKeyName, ConfigurableApplicationContext context) {
        if (!modelToDataset.containsKey(modelClass)) {
            DataExtractor extractor = extractorResolver.resolve(pathToSource);
            Dataset<Row> dataset = extractor.load(pathToSource, context);
            dataset.persist();
            modelToDataset.put(modelClass, dataset);
        }

        Encoder<?> encoder = Encoders.bean(modelClass);
        return modelToDataset.get(modelClass).filter(functions.col(foreignKeyName).equalTo(ownerId))
                .as(encoder).collectAsList();
    }
}
