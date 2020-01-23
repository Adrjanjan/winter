package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.schemas.TableSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InheritanceMapping {
    // mapa nazwa pola -> tabela w której znajduje sie dane pole
    private Map<String, TableSchema> fieldNameToTable;

    public InheritanceMapping(Map<String, TableSchema> fieldNameToTable) {
        this.fieldNameToTable = fieldNameToTable;
    }

    public List<TableSchema> getAllTableSchemas() {
        return new ArrayList<>(this.fieldNameToTable.values());
    }

    public TableSchema getTableSchema(String fieldName) {
        return fieldNameToTable.get(fieldName);
    }

    public void union(Map<String, TableSchema> otherMapping) {
        this.fieldNameToTable.putAll(otherMapping);
    }
}
