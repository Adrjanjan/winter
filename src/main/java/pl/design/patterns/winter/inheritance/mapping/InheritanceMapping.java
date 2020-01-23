package pl.design.patterns.winter.inheritance.mapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.design.patterns.winter.schemas.TableSchema;

public class InheritanceMapping {
    private Map<String, TableSchema> fieldNameToTable;

    public InheritanceMapping(Map<String, TableSchema> fieldNameToTable) {
        this.fieldNameToTable = fieldNameToTable;
    }

    public Set<TableSchema> getAllTableSchemas() {
        return new HashSet<>(this.fieldNameToTable.values());
    }

    public TableSchema getTableSchema(String fieldName) {
        return fieldNameToTable.get(fieldName);
    }

    public void union(Map<String, TableSchema> otherMapping) {
        this.fieldNameToTable.putAll(otherMapping);
    }
}
