package ru.nexgen.SparkDataStarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SparkDataStarterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SparkDataStarterApplication.class, args);

        SpeakerRepo speakerRepo = context.getBean(SpeakerRepo.class);
        speakerRepo.findByAgeBetween(20, 35).forEach(System.out::println);

        CriminalRepo criminalRepo = context.getBean(CriminalRepo.class);
        criminalRepo.findByNumberGreaterThanOrderByNumber(14).forEach(System.out::println);

        long byNameContains = criminalRepo.findByNameContainsCount("ova");
        System.out.println("count: " + byNameContains);
    }

}
