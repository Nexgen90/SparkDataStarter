package ru.nexgen.unsafe_starter.utils;

import lombok.Data;
import lombok.experimental.Delegate;

import java.util.List;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
@Data
public class LazySparkList implements List {

    @Delegate
    private List content;

    private long ownerId;

    private Class<?> modelClass;

    private String foreignKeyName;

    private String pathToSource;

    public boolean isInitialized() {
        return content != null && !content.isEmpty();
    }
}
