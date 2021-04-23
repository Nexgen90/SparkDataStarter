package ru.nexgen.unsafe_starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nexgen.unsafe_starter.dataextractor.DataExtractor;

import java.util.Map;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
@Component
public class DataExtractorResolver {
    @Autowired
    private Map<String, DataExtractor> extractorMap;

    public DataExtractor resolve(String pathToData) {
        String fileExtension = pathToData.split("\\.")[1];
        return extractorMap.get(fileExtension);
    }
}
