package pl.design.patterns.winter.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.DatabaseField;

public abstract class QueryBuilder {
    //public abstract <T> String prepare(T object) throws InvocationTargetException, IllegalAccessException;

    public abstract <T> QueryBuilder setObject(T object);
    public abstract QueryBuilder createOperation();
    public abstract QueryBuilder setTable();
    public abstract QueryBuilder setFields();
    public abstract QueryBuilder setValues() throws InvocationTargetException, IllegalAccessException;
    public abstract String generate();

    @SuppressWarnings("unchecked")
    <T> List<Field> getFieldsToIncludeInQuery(T object) {
        List<Field> fields = new ArrayList<>(Arrays.asList(object.getClass()
                .getDeclaredFields()));

        Class<? super T> superclass = (Class<? super T>) object.getClass()
                .getSuperclass();

        while (superclass != null && !superclass.equals(Object.class)) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }

        return fields.stream()
                .filter(field -> !field.getName()
                        .startsWith("this") && field.isAnnotationPresent(DatabaseField.class))
                .collect(Collectors.toList());
    }
}
