package ru.nexgen.SparkDataStarter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nexgen.unsafe_starter.ForeignKey;
import ru.nexgen.unsafe_starter.dataextractor.Source;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Source("data/criminals.csv")
public class Criminal {
    private long id;
    private String name;
    private int number;

    @ForeignKey("criminalId")
    private List<Order> orders;
}
