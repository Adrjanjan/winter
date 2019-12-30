package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.schemas.TableSchema;

public class ClassTableMapping<T> implements InheritanceMapping {

    @Override
    public TableSchema getTableSchema(Class clazz) {
        return null;
    }
}
