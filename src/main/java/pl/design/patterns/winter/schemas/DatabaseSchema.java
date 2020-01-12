package pl.design.patterns.winter.schemas;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

@Component
@Scope("singleton")
public class DatabaseSchema {

    private Map<Class<?>, InheritanceMapping> classToTable = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> InheritanceMapping getMapping(Class<T> clazz) {
        return classToTable.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public <T> InheritanceMapping addMapping(Class<T> clazz, InheritanceMapping inheritanceMapping) {
        return classToTable.put(clazz, inheritanceMapping);
    }

}
