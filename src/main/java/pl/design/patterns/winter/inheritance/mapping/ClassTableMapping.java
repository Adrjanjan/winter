package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.schemas.TableSchema;

import lombok.Builder;

@Builder
public class ClassTableMapping<T> implements InheritanceMapping {
    private TableSchema<T> tableSchema;

    private InheritanceMapping parent;

    @Override
    public TableSchema getTableSchema(Class clazz) {
        // return tableSchema.union(parent);
        return null;
    }
}
