package ru.nexgen.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.nexgen.unsafe_starter.utils.FirstLevelCacheService;
import ru.nexgen.unsafe_starter.utils.LazyCollectionAspectHandler;
import ru.nexgen.unsafe_starter.utils.LazySparkList;

/**
 * Created by nikolay.mikutskiy
 * Date: 23.04.2021
 */
@Configuration
public class StarterConf {

    @Bean
    @Scope("prototype")
    public LazySparkList lazySparkList() {
        return new LazySparkList();
    }

    @Bean
    public FirstLevelCacheService firstLevelCacheService() {
        return new FirstLevelCacheService();
    }

    @Bean
    public LazyCollectionAspectHandler lazyCollectionAspectHandler() {
        return new LazyCollectionAspectHandler();
    }
}
