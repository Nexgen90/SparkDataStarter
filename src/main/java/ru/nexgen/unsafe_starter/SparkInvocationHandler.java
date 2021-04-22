package ru.nexgen.unsafe_starter;

import lombok.Builder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;
import ru.nexgen.unsafe_starter.dataextractor.DataExtractor;
import ru.nexgen.unsafe_starter.strategy.finalizer.Finalizer;
import ru.nexgen.unsafe_starter.strategy.transformation.SparkTransformation;
import ru.nexgen.unsafe_starter.utils.OrderedBag;
import scala.Tuple2;

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
    private Map<Method, List<Tuple2<SparkTransformation, List<String>>>> transformationChain;
    private Map<Method, Finalizer> finalizerMap;
    private ConfigurableApplicationContext context;

    /**
     * @param method метод, котоый вызвали выше
     */
    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        //1) Добываем данные
        Dataset<Row> dataset = dataExtractor.load(pathToData, context);

        //2) Делаем все необходимые трансформации
        List<Tuple2<SparkTransformation, List<String>>> tuple2List = transformationChain.get(method);
        for (Tuple2<SparkTransformation, List<String>> tuple : tuple2List) {
            SparkTransformation transformation = tuple._1();
            List<String> columnName = tuple._2();
            dataset = transformation.transform(dataset, columnName, new OrderedBag<>(args));
        }

        //3) Приводим к конечному результату
        Finalizer finalizer = finalizerMap.get(method);
        Object retVal = finalizer.doAction(dataset, modelClass);
        return retVal;
    }
}
