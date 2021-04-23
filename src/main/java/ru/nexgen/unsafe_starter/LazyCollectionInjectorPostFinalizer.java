package ru.nexgen.unsafe_starter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;
import ru.nexgen.unsafe_starter.dataextractor.Source;
import ru.nexgen.unsafe_starter.utils.LazySparkList;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
@RequiredArgsConstructor
public class LazyCollectionInjectorPostFinalizer implements PostFinalizer {
    private final ConfigurableApplicationContext realContext;

    @SneakyThrows
    @Override
    public Object postFinalizer(Object retVal) {
        if (Collection.class.isAssignableFrom(retVal.getClass())) {
            for (Object model : (List) retVal) {
                Field idField = model.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                long ownerId = idField.getLong(model);
                Field[] fields = model.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (List.class.isAssignableFrom(field.getType())) {
                        LazySparkList sparkList = realContext.getBean(LazySparkList.class);
                        sparkList.setOwnerId(ownerId);
                        String columnName = field.getAnnotation(ForeignKey.class).value();
                        sparkList.setForeignKeyName(columnName);
                        Class<?> embeddedModel = getEmbeddedModel(field);
                        sparkList.setModelClass(embeddedModel);
                        String pathToData = embeddedModel.getAnnotation(Source.class).value();
                        sparkList.setPathToSource(pathToData);

                        field.setAccessible(true);
                        field.set(model,sparkList);
                    }
                }
            }
        }
        return retVal;
    }
    private Class<?> getEmbeddedModel(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Class<?> embeddedModel = (Class<?>) genericType.getActualTypeArguments()[0];
        return embeddedModel;
    }
}
