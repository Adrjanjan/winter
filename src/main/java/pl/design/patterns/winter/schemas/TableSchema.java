package pl.design.patterns.winter.schemas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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

    private Map<Class<?>, String> discriminatorValues = new HashMap<>();

    public void addColumns(Collection<ColumnSchema> columnSchemas) {
        columns.addAll(columnSchemas);
    }

    public void addDiscriminatorValue(Class<?> clazz, String discriminatorValue) {
        this.discriminatorValues.put(clazz, discriminatorValue);
    }
}
