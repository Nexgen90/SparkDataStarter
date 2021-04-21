package ru.nexgen.unsafe_starter;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Чтобы помечать поля модели, которые нужны для бизнес-логики, а не для данных
 * Created by nikolay.mikutskiy
 * Date: 19.04.2021
 */
@Retention(RUNTIME)
@interface Transient {
}
