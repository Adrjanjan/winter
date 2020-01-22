package pl.design.patterns.winter.schemas;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TableSchema {
    private Class clazz;

    private String tableName;

    private List<ColumnSchema> columns;

    private ColumnSchema idField;

    public void addColumns(List<ColumnSchema> columnSchemas)
    {
        columns.addAll(columnSchemas);
    }
}
