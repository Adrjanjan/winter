package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.*;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;

public class ConcreteTableInheritanceMapper extends InheritanceMapper {

    @Override
    <T> InheritanceMapping mapInheritance(Class<T> clazz) {

        Set<Field> fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        final var idField = getIdField(clazz, fields);

        Class<? super T> superclass = clazz.getSuperclass();

        while (superclass != null && !superclass.equals(Object.class)) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }

        final var columnSchemas = createColumnSchemas(fields);

        TableSchema<T> tableSchema = TableSchema.<T> builder()
                .clazz(clazz)
                .tableName(resolveTableName(clazz))
                .columns(columnSchemas)
                .idField(idField)
                .build();

        final Map<String, TableSchema> mapping = new HashMap<>();
        fields.stream()
                .map(Field::getName)
                .forEach(name -> mapping.put(name, tableSchema));

        return new InheritanceMapping(mapping);
    }
}
