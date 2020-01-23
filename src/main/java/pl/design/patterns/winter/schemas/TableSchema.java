package pl.design.patterns.winter.schemas;

import java.util.Collection;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSchema {
    private Class clazz;

    private String tableName;

    private Set<ColumnSchema> columns;

    private ColumnSchema idField;

    public void addColumns(Collection<ColumnSchema> columnSchemas) {
        columns.addAll(columnSchemas);
    }
}
