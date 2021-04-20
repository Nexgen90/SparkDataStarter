package ru.nexgen.starter;

import java.util.Map;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public class DataExtractorResolver {
    private Map<String, DataExtractor> extractorMap;

    DataExtractor resolve(String pathToData) {
        String fileExtension = pathToData.split("\\.")[1];
        return extractorMap.get(fileExtension);
    }
}
