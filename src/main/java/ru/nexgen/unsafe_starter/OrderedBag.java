package ru.nexgen.unsafe_starter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by nikolay.mikutskiy
 * Date: 21.04.2021
 */
public class OrderedBag<T> {
    private List<T> list = new ArrayList<>();

    public OrderedBag(Object[] args) {
        this.list = new ArrayList(asList(args));
    }

    public T takeAndRemove() {
        return list.remove(0);
    }

    public int size() {
        return list.size();
    }
}
