package br.com.magnumbank.api1.cache;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class SortUtils {

    public static String sortToKey(Sort sort) {
        return sort == null || sort.isUnsorted() ? "" : sort.toString();
    }

    public static  Sort keyToSort(String key){
        if (key == null || key.isBlank()) return Sort.unsorted();

        return Sort.by(Arrays.stream(key.split(","))
                .map(String::trim)
                .map(s -> s.split(":"))
                .map(arr -> {
                    String prop = arr[0].trim();
                    String dir = arr.length > 1 ? arr[1].trim() : "ASC";
                    return new Sort.Order(Sort.Direction.fromString(dir), prop);
                })
                .toList());
    }
}
