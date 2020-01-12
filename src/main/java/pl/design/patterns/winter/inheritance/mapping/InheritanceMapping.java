package pl.design.patterns.winter.inheritance.mapping;

import java.util.Map;

import pl.design.patterns.winter.schemas.TableSchema;

public class InheritanceMapping {
    private Map<String, TableSchema> columnNameToTable;

    public InheritanceMapping(Map<String, TableSchema> columnNameToTable) {
        this.columnNameToTable = columnNameToTable;
    }

    public TableSchema getTableSchema(String columnName) {
        return columnNameToTable.get(columnName);
    }

    public void union(Map<String, TableSchema> otherMapping) {
        this.columnNameToTable.putAll(otherMapping);
    }
}
