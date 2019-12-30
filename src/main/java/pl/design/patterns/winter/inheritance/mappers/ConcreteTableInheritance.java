package pl.design.patterns.winter.inheritance.mappers;

import static pl.design.patterns.winter.inheritance.mapping.InheritanceMappingType.CONCRETE_TABLE;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.NoIdFieldException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

public class ConcreteTableInheritance implements InheritanceMapper {

    @Override
    public <T> InheritanceMapping<T> map(Class<T> clazz) {
        return map(clazz, new InheritanceMapping<T>());
    }

    private <T> InheritanceMapping<T> map(Class<? super T> clazz, InheritanceMapping<T> inheritanceMapping) {
        Set<Field> fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        final var idField = getIdField(clazz, fields);

        Class<? super T> superclass = clazz.getSuperclass();
        if ( superclass != null && !superclass.equals(Object.class) ) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
        }

        TableSchema<T> tableSchema = TableSchema.<T> builder()
                .inheritanceType(CONCRETE_TABLE)
                .clazz(clazz)
                .tableName(resolveTableName(clazz))
                .fields(createColumnSchemas(fields))
                .idField(idField)
                .build();

        inheritanceMapping.addHierarchy(clazz, tableSchema);
        superclass = clazz.getSuperclass();
        if ( superclass != null && !superclass.equals(Object.class) ) {
            map(superclass, inheritanceMapping);
        }
        return inheritanceMapping;
    }

    private List<ColumnSchema> createColumnSchemas(Set<Field> fields) {
        return fields.stream()
                .filter(f -> f.isAnnotationPresent(DatabaseField.class))
                .map(ColumnSchema::new)
                .collect(Collectors.toList());
    }

    private <T> ColumnSchema getIdField(Class<? super T> clazz, Set<Field> fields) {
        return fields.stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(ColumnSchema::new)
                .findFirst()
                .orElseThrow(NoIdFieldException::new);
    }

    private <T> String resolveTableName(Class<T> clazz) {
        if ( clazz.isAnnotationPresent(DatabaseTable.class) ) {
            return clazz.getAnnotation(DatabaseTable.class)
                    .name();
        }
        return clazz.getName();
    }
}
