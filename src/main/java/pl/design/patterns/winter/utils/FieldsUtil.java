package pl.design.patterns.winter.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.DatabaseField;

public class FieldsUtil {

    @SuppressWarnings("unchecked")
    public static <T> List<Field> getAllFieldsInClassHierarchy(Class<T> clazz) {
        List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));

        Class<? super T> superclass = clazz.getSuperclass();

        while (superclass != null && !superclass.equals(Object.class)) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }

        return fields.stream()
                .filter(field -> field.isAnnotationPresent(DatabaseField.class) //
                        && !field.getName()
                                .startsWith("this"))
                .collect(Collectors.toList());
    }
}