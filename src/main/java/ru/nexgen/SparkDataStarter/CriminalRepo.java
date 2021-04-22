package ru.nexgen.SparkDataStarter;

import ru.nexgen.unsafe_starter.SparkRepository;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
public interface CriminalRepo extends SparkRepository<Criminal> {
    List<Criminal> findByNumberGreaterThanOrderByNumber(int min);

    long findByNameContainsCount(String partOfName);
}
