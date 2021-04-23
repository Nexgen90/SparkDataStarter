package ru.nexgen.SparkDataStarter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nexgen.unsafe_starter.dataextractor.Source;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Source("data/orders.csv")
public class Order {
    private String name;
    private String desc;
    private int price;
    private long criminalId;
}
