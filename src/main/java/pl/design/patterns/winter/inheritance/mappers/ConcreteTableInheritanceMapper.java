package pl.design.patterns.winter.inheritance.mappers;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.NameUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ConcreteTableInheritanceMapper extends InheritanceMapper {

    private DatabaseSchema databaseSchema;

    public ConcreteTableInheritanceMapper(DatabaseSchema databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    @Override
    public <T> InheritanceMapping map(Class<T> clazz) {

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

        TableSchema tableSchema = TableSchema.builder()
                .clazz(clazz)
                .tableName(NameUtils.extractTableName(clazz))
                .columns(columnSchemas)
                .idField(idField)
                .build();

        final Map<String, TableSchema> columnMapping = new HashMap<>();
        fields.stream()
                .map(Field::getName)
                .forEach(name -> columnMapping.put(name, tableSchema));
        final var mapping = new InheritanceMapping(columnMapping);
        databaseSchema.addMapping(clazz, mapping);
        return mapping;
    }
}
