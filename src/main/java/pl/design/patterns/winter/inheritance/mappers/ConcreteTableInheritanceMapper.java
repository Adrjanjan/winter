package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;

public class ConcreteTableInheritanceMapper extends InheritanceMapper {

    @Override
    <T> InheritanceMapping map(Class<T> clazz) {

        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        final var idField = getIdField(fields);

        Class<? super T> superclass = clazz.getSuperclass();

        while (superclass != null && !superclass.equals(Object.class)) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }
        fields = fields.stream()
                .filter(f -> !f.getName()
                        .startsWith("this"))
                .collect(Collectors.toList());

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
