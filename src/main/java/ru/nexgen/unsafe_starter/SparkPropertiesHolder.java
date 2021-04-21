package ru.nexgen.unsafe_starter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "spark")
public class SparkPropertiesHolder {
    private String appName;
    private String packagesToScan;
}
