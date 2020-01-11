package pl.design.patterns.winter.inheritance.mapping;

import java.util.HashMap;
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

    public InheritanceMapping union(InheritanceMapping other) {
        var map = new HashMap<>(this.columnNameToTable);
        map.putAll(other.columnNameToTable);
        return new InheritanceMapping(map);
    }
}
