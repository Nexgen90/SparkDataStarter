package ru.nexgen.starter;

import org.springframework.context.ConfigurableApplicationContext;

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
public class SparkInvocationHandlerFactory {
    private DataExtractorResolver resolver;
    private Map<String, TransformationSpider> spiderMap;
    private Map<String, Finalizer> finalizerMap;
    private ConfigurableApplicationContext context;

    public SparkInvocationHandler create(Class<? extends SparkRepository> sparkRepoInterface) {
        Class<?> modelClass = getModelClass(sparkRepoInterface);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        Set<String> fieldNames = getFieldNames(modelClass);
        DataExtractor dataExtractor = resolver.resolve(pathToData);
        Map<Method, List<SparkTransformation>> transformationChain = new HashMap<>();
        Map<Method, Finalizer> methodToFinalizer = new HashMap<>();

        Method[] methods = sparkRepoInterface.getMethods();
        for (Method method : methods) {
            TransformationSpider currentStrategy = null;
            String name = method.getName();
            List<String> methodWords = WordsMatcher.toWordsByJavaConvention(name);
            List<SparkTransformation> transformations = new ArrayList<>();
            while (methodWords.size() > 1) {
                String spiderName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(spiderMap.keySet(), methodWords);
                if (!spiderName.isEmpty()) {
                    currentStrategy = spiderMap.get(spiderName);
                }
                transformations.add(currentStrategy.getTransformation(methods));
            }
            transformationChain.put(method, transformations);
            String finalizerName = "collect";
            if (methodWords.size() == 1) {
                finalizerName = methodWords.get(0);
            }
            methodToFinalizer.put(method, finalizerMap.get(finalizerName));
        }

        return SparkInvocationHandler.builder()
                .modelClass(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor)
                .transformationChain(transformationChain)
                .finalizerMap(methodToFinalizer)
                .context(context)
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
