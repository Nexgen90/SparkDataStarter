package ru.nexgen.SparkDataStarter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nexgen.unsafe_starter.dataextractor.Source;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/speakers.json")
public class Speaker {
    private long id;
    private String name;
    private long age;
}
