package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.schemas.TableSchema;

public interface InheritanceMapping<T> {

    TableSchema<? super T> getTableSchema(Class<T> clazz);
}
