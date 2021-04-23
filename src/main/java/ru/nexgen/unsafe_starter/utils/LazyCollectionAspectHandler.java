package ru.nexgen.unsafe_starter.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
@Aspect
public class LazyCollectionAspectHandler {
    @Autowired
    private FirstLevelCacheService cacheService;
    @Autowired
    private ConfigurableApplicationContext context;

    @Before("execution(* ru.nexgen.unsafe_starter.utils.LazySparkList.*(..)) && execution(* java.util.List.*(..))")
    public void setLazyCollections(JoinPoint jp) {
        LazySparkList lazyList = (LazySparkList) jp.getTarget();

        if (!lazyList.isInitialized()) {
            List<Object> content = cacheService.readDataFor(lazyList.getOwnerId(), lazyList.getModelClass(),
                    lazyList.getPathToSource(), lazyList.getForeignKeyName(), context);
            lazyList.setContent(content);
        }
    }
}
