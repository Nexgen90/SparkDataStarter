package ru.nexgen.unsafe_starter;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by nikolay.mikutskiy
 * Date: 22.04.2021
 */
@Retention(RUNTIME)
public @interface ForeignKey {
    String value();
}
