package pl.design.patterns.winter.schemas;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSchema<T> {
    private Class clazz;

    private String tableName;

    private List<ColumnSchema> columns;

    private ColumnSchema idField;

}
