package ru.nexgen.SparkDataStarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class SparkDataStarterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SparkDataStarterApplication.class, args);

        SpeakerRepo speakerRepo = context.getBean(SpeakerRepo.class);
        List<Speaker> byAgeBetween = speakerRepo.findByAgeBetween(20, 35);

    }

}
