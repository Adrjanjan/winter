package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.schemas.TableSchema;

public class ConcreteTableMapping<T> implements InheritanceMapping {
    private TableSchema<T> tableSchema;

    public ConcreteTableMapping(TableSchema<T> tableSchema) {
        this.tableSchema = tableSchema;
    }

    @Override
    public TableSchema getTableSchema(Class clazz) {
        return tableSchema;
    }
}
