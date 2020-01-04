package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.NoIdFieldException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;

public abstract class InheritanceMapper {

    public abstract <T> InheritanceMapping<T> map(Class<T> clazz);

    List<ColumnSchema> createColumnSchemas(Set<Field> fields) {
        return fields.stream()
                .filter(f -> f.isAnnotationPresent(DatabaseField.class))
                .map(ColumnSchema::new)
                .collect(Collectors.toList());
    }

    <T> ColumnSchema getIdField(Class<? super T> clazz, Set<Field> fields) {
        return fields.stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(ColumnSchema::new)
                .findFirst()
                .orElseThrow(NoIdFieldException::new);
    }

    <T> String resolveTableName(Class<T> clazz) {
        if ( clazz.isAnnotationPresent(DatabaseTable.class) ) {
            return clazz.getAnnotation(DatabaseTable.class)
                    .name();
        }
        return clazz.getName();
    }
}
