package ru.nexgen.unsafe_starter.dataextractor;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
@Retention(RUNTIME)
public @interface Source {
    String value();
}
