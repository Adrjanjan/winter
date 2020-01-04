package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import pl.design.patterns.winter.inheritance.mapping.ClassTableMapping;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;

public class ClassTableInheritance extends InheritanceMapper {
    @Override
    public <T> InheritanceMapping<T> map(Class<T> clazz) {
        if ( clazz == null || clazz.equals(Object.class) ) {
            return null;
        }

        Set<Field> fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        final var idField = getIdField(clazz, fields);

        TableSchema<T> tableSchema = TableSchema.<T> builder()
                .clazz(clazz)
                .tableName(resolveTableName(clazz))
                .columns(createColumnSchemas(fields))
                .idField(idField)
                .build();

        return ClassTableMapping.<T> builder()
                .tableSchema(tableSchema)
                .parent(map(clazz.getSuperclass()))
                .build();
    }
}
