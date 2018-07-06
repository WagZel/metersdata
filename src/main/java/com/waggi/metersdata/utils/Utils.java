package com.waggi.metersdata.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Utils {

    public static <T> List<T> newArrayList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public static String concatenateStringStream(Stream<String> stream, String delimiter) {
        String result = stream.reduce("", (reduce, current) -> String.format("%s%s%s", reduce, delimiter, current));
        return result.length() >= delimiter.length() ? result.substring(delimiter.length()) : result;
    }
}
