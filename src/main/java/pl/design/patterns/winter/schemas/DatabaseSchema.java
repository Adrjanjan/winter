package pl.design.patterns.winter.schemas;

import java.util.HashMap;
import java.util.Map;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

public class DatabaseSchema {
    private Map<Class<?>, InheritanceMapping<?>> classToTable = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> InheritanceMapping<T> getMapping(Class<T> clazz) {
        return (InheritanceMapping<T>) classToTable.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public <T> InheritanceMapping<T> addMapping(Class<T> clazz, InheritanceMapping inheritanceMapping) {
        return (InheritanceMapping<T>) classToTable.put(clazz, inheritanceMapping);
    }

}
