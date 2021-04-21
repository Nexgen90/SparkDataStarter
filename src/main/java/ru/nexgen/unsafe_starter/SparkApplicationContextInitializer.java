package ru.nexgen.unsafe_starter;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;
import java.util.Set;

import static java.beans.Introspector.decapitalize;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
public class SparkApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        //tempContext - контекст для наскройки копонент стартера. Нужен свой, потому что на этом эпапе спринг ещё не создаст свой
        //имя пакета для сканировани в стартере не должно совпадать с именем пакета, где будут приложения. Поэтому сделан префикс unsafe
        AnnotationConfigApplicationContext tempContext = new AnnotationConfigApplicationContext("ru.nexgen.unsafe_starter");
        SparkInvocationHandlerFactory factory = tempContext.getBean(SparkInvocationHandlerFactory.class);
        tempContext.close();


        factory.setRealContext(context);
        registerSparkBean(context);
        String packagesToScan = context.getEnvironment().getProperty("spark.packages-to-scan");
        Reflections scanner = new Reflections(packagesToScan);
        Set<Class<? extends SparkRepository>> sparkRepoInterfaces = scanner.getSubTypesOf(SparkRepository.class);
        sparkRepoInterfaces.forEach(sparkRepoInterface -> {
            Object golem = Proxy.newProxyInstance(sparkRepoInterface.getClassLoader(),
                    new Class[]{sparkRepoInterface},
                    factory.create(sparkRepoInterface));
            context.getBeanFactory().registerSingleton(
                    decapitalize(sparkRepoInterface.getSimpleName()),
                    golem);
        });
    }

    private void registerSparkBean(ConfigurableApplicationContext context) {
        String appName = context.getEnvironment().getProperty("spark.app-name");
        SparkSession sparkSession = SparkSession.builder().appName(appName).master("local[*]").getOrCreate();
        JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
        context.getBeanFactory().registerSingleton("sparkContext", sparkContext);
        context.getBeanFactory().registerSingleton("sparkSession", sparkSession);
    }
}
