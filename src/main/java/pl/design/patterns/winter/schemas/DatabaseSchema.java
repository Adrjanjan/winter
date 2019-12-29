package pl.design.patterns.winter.schemas;

import java.util.HashMap;
import java.util.Map;

public class DatabaseSchema {
    private Map<Class<?>, TableSchema<?>> classToTable = new HashMap<>();

    @SuppressWarnings("unchecked")
    <T> TableSchema<T> getMapping(Class<T> clazz) {
        return (TableSchema<T>) classToTable.get(clazz);
    }

    @SuppressWarnings("unchecked")
    <T> TableSchema<T> addMapping(Class<T> clazz, TableSchema<T> tableSchema) {
        return (TableSchema<T>) classToTable.put(clazz, tableSchema);
    }

}
