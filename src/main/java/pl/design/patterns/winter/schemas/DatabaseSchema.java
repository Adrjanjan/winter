package pl.design.patterns.winter.schemas;

import java.util.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

@Component
@Scope("singleton")
public class DatabaseSchema {

    private Map<Class<?>, InheritanceMapping> classToTable = new HashMap<>();

    private Set<TableSchema> tables = new HashSet<>();

    public InheritanceMapping getMapping(Class<?> clazz) {
        return classToTable.get(clazz);
    }

    public InheritanceMapping addMapping(Class<?> clazz, InheritanceMapping inheritanceMapping) {
        return classToTable.put(clazz, inheritanceMapping);
    }

    public Set<TableSchema> getAllTables() {
        return tables;
    }

    public void addTableSchemas(Collection<TableSchema> tableSchemas) {
        tables.addAll(tableSchemas);
    }

}
