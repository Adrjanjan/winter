package pl.design.patterns.winter.schemas;

import java.util.List;

import pl.design.patterns.winter.exceptions.NoSuchColumnException;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSchema<T> {
    private Class clazz;

    private String tableName;

    private List<ColumnSchema> columns;

    private ColumnSchema idField;

    public ColumnSchema getColumnByName(String name) {
        return columns.stream()
                .filter(column -> column.getColumnName()
                        .equals(name))
                .findFirst()
                .orElseThrow(NoSuchColumnException::new);
    }

    public TableSchema withNewColumns(List<ColumnSchema> columns) {
        return builder().clazz(clazz)
                .columns(columns)
                .idField(idField)
                .build();
    }
}
