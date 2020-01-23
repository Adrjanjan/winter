package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import pl.design.patterns.winter.inheritance.mapping.ClassTableInheritanceMapping;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.NameUtils;

public class ClassTableInheritanceMapper extends InheritanceMapper {

    private final DatabaseSchema databaseSchema;

    public ClassTableInheritanceMapper(DatabaseSchema databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    @Override
    public <T> InheritanceMapping map(Class<T> clazz) {
        Class<? super T> superclass = clazz.getSuperclass();

        if ( clazz.equals(Object.class) ) {
            return new InheritanceMapping(Collections.emptyMap());
        }

        InheritanceMapping parentMapping = databaseSchema.getMapping(superclass);
        if ( parentMapping == null ) {
            parentMapping = this.map(superclass);
            databaseSchema.addMapping(superclass, parentMapping);
        }

        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        fields = fields.stream()
                .filter(f -> !f.getName()
                        .startsWith("this"))
                .collect(Collectors.toList());

        Class<? super T> root = clazz;
        while ( !root.getSuperclass()
                .equals(Object.class)) {
            root = root.getSuperclass();
        }
        ColumnSchema rootIdField = getIdField(Arrays.asList(root.getDeclaredFields()));

        Set<ColumnSchema> columnSchemas = createColumnSchemas(fields);
        if ( !columnSchemas.contains(rootIdField) ) {
            columnSchemas.add(rootIdField);
        }

        TableSchema tableSchema = TableSchema.builder()
                .clazz(clazz)
                .tableName(NameUtils.extractTableName(clazz))
                .columns(columnSchemas)
                .idField(rootIdField)
                .build();

        final Map<String, TableSchema> columnMapping = new HashMap<>();
        fields.stream()
                .map(Field::getName)
                .forEach(name -> columnMapping.put(name, tableSchema));

        final var mapping = new ClassTableInheritanceMapping(columnMapping, parentMapping);
        databaseSchema.addMapping(clazz, mapping);
        return mapping;

    }
}
