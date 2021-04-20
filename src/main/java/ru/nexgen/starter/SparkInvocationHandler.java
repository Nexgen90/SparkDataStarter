package ru.nexgen.starter;

import lombok.Builder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
@Builder
public class SparkInvocationHandler implements InvocationHandler {
    private Class<?> modelClass;
    private String pathToData;
    private DataExtractor dataExtractor;
    private Map<Method, List<SparkTransformation>> transformationChain;
    private Map<Method, Finalizer> finalizerMap;
    private ConfigurableApplicationContext context;

    /**
     * @param method метод, котоый вызвали выше
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //1) Добываем данные
        Dataset<Row> dataset = dataExtractor.load(pathToData, context);

        //2) Делаем все необходимые трансформации
        List<SparkTransformation> transformations = transformationChain.get(method);
        for (SparkTransformation transformation : transformations) {
            dataset = transformation.transform(dataset);
        }

        //3) Приводим к конечному результату
        Finalizer finalizer = finalizerMap.get(method);
        Object retVal = finalizer.doAction(dataset);
        return retVal;
    }
}
