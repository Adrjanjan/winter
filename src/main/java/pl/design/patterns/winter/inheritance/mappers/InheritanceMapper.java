package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.NoIdFieldException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.DatabaseSchema;

public abstract class InheritanceMapper {

    @Autowired
    DatabaseSchema databaseSchema;

    public <T> void map(Class<T> clazz) {
        InheritanceMapping mapping = mapInheritance(clazz);
        databaseSchema.addMapping(clazz, mapping);
    }

    abstract <T> InheritanceMapping mapInheritance(Class<T> clazz);

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
