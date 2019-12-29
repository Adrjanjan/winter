package pl.design.patterns.winter.schemas;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSchema<T> {
    TableSchema<? super T> parent;

    private Class<? super T> clazz;

    private String tableName;

    private List<ColumnSchema> fields;

    private Map<Class<T>, ColumnSchema> classDiscriminators;

    private ColumnSchema idField;
}
