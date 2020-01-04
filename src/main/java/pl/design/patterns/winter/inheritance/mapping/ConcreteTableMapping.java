package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.schemas.TableSchema;

public class ConcreteTableMapping<T> implements InheritanceMapping {
    private TableSchema<T> concreteClassSchema;

    public ConcreteTableMapping(TableSchema<T> tableSchema) {
        this.concreteClassSchema = tableSchema;
    }

    @Override
    public TableSchema getTableSchema(Class clazz) {
        return concreteClassSchema;
    }
}
