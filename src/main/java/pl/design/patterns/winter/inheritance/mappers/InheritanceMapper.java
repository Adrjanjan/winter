package pl.design.patterns.winter.inheritance.mappers;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.NoIdFieldException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InheritanceMapper {

    public abstract <T> InheritanceMapping map(Class<T> clazz);

    List<ColumnSchema> createColumnSchemas(List<Field> fields) {
        return fields.stream()
                .filter(f -> f.isAnnotationPresent(DatabaseField.class))
                .map(ColumnSchema::new)
                .collect(Collectors.toList());
    }

    ColumnSchema getIdField(List<Field> fields) {
        return fields.stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(ColumnSchema::new)
                .findFirst()
                .orElseThrow(NoIdFieldException::new);
    }

    String resolveTableName(Class<?> clazz) {
        final var name = clazz.getAnnotation(DatabaseTable.class)
                    .name();
        return "".equals(name) ? clazz.getSimpleName() : name;
    }
}
