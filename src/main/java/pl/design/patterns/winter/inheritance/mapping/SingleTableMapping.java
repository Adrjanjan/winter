package pl.design.patterns.winter.inheritance.mapping;

import java.util.List;
import java.util.Map;

import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

public class SingleTableMapping<T> implements InheritanceMapping {
    private TableSchema<? super T> tableSchema;

    private Map<Class<T>, List<ColumnSchema>> classToColumns;

    @Override
    public TableSchema getTableSchema(Class clazz) {
        return null;
    }

}
