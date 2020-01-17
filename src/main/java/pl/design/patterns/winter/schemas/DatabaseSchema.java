package pl.design.patterns.winter.schemas;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

import java.util.*;

@Component
@Scope("singleton")
public class DatabaseSchema {

    private Map<Class<?>, InheritanceMapping> classToTable = new HashMap<>();
    private Set<TableSchema> tables = new HashSet<>();

    @SuppressWarnings("unchecked")
    public InheritanceMapping getMapping(Class<?> clazz) {
        return classToTable.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public InheritanceMapping addMapping(Class<?> clazz, InheritanceMapping inheritanceMapping) {
        return classToTable.put(clazz, inheritanceMapping);
    }

    public Set<TableSchema> getAllTables() {
        return tables;
    }

    public void addTableSchemas(List<TableSchema> tableSchemas) {
        tables.addAll(tableSchemas);
    }

}
