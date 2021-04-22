package ru.nexgen.unsafe_starter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.nexgen.unsafe_starter.dataextractor.DataExtractor;
import ru.nexgen.unsafe_starter.dataextractor.Source;
import ru.nexgen.unsafe_starter.strategy.TransformationSpider;
import ru.nexgen.unsafe_starter.strategy.finalizer.Finalizer;
import ru.nexgen.unsafe_starter.strategy.transformation.SparkTransformation;
import ru.nexgen.unsafe_starter.utils.WordsMatcher;
import scala.Tuple2;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 *                    FieldName           FieldName        FieldName FieldName
 *                       |                    |                 |     |
 *                  ,------------,          ,--,              ,--,  ,---,
 * List<User> findByNameOfБабушкаContainsAndAgeLessThanOrderByAgeAndNameSave
 *       |    '----'             '______'      '------''-----'          '--'
 *      \|/     |                    '------------'       |              |
 *       '      |                          |              |          Finalizer
 *     Model    |                 FilterTransformation    |
 *       |      ------------- StrategyName ----------------
 *      \|/
 *       '
 *   PathToSource
 */
@Component
@RequiredArgsConstructor
public class SparkInvocationHandlerFactory {
    private final DataExtractorResolver resolver;
    private final Map<String, TransformationSpider> spiderMap;
    private final Map<String, Finalizer> finalizerMap;

    @Setter
    private ConfigurableApplicationContext realContext;

    public SparkInvocationHandler create(Class<? extends SparkRepository> sparkRepoInterface) {
        Class<?> modelClass = getModelClass(sparkRepoInterface);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        Set<String> fieldNames = getFieldNames(modelClass);
        DataExtractor dataExtractor = resolver.resolve(pathToData);
        Map<Method, List<Tuple2<SparkTransformation, List<String>>>> transformationChain = new HashMap<>();
        Map<Method, Finalizer> methodToFinalizer = new HashMap<>();

        Method[] methods = sparkRepoInterface.getMethods();
        for (Method method : methods) {
            TransformationSpider currentStrategy = null;
            String name = method.getName();
            List<String> methodWords = WordsMatcher.toWordsByJavaConvention(name);
            List<Tuple2<SparkTransformation, List<String>>> transformations = new ArrayList<>();
            while (methodWords.size() > 1) {
                String spiderName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(spiderMap.keySet(), methodWords);
                if (!spiderName.isEmpty()) {
                    currentStrategy = spiderMap.get(spiderName);
                }
                transformations.add(currentStrategy.getTransformation(methodWords, fieldNames));
            }
            transformationChain.put(method, transformations);
            String finalizerName = "collect";
            if (methodWords.size() == 1) {
                finalizerName = Introspector.decapitalize(methodWords.get(0));
            }
            methodToFinalizer.put(method, finalizerMap.get(finalizerName));
        }

        return SparkInvocationHandler.builder()
                .modelClass(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor)
                .transformationChain(transformationChain)
                .finalizerMap(methodToFinalizer)
                .context(realContext)
                .build();
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> repoInterface) {
        ParameterizedType genericInterface = (ParameterizedType) repoInterface.getGenericInterfaces()[0];
        Class<?> modelClass = (Class<?>) genericInterface.getActualTypeArguments()[0];
        return modelClass;
    }

    private Set<String> getFieldNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !Collection.class.isAssignableFrom(field.getType()))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
}
