package ru.nexgen.SparkDataStarter;

import ru.nexgen.unsafe_starter.SparkRepository;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public interface SpeakerRepo extends SparkRepository<Speaker> {
    List<Speaker> findByAgeBetween(int min, int max);
    long findByAgeGreaterThanCount(int min);
}
