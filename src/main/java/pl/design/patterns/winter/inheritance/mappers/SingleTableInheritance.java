package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.annotations.DiscriminatorValue;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.inheritance.mapping.SingleTableMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.schemas.TableSchema;

@Component
public class SingleTableInheritance extends InheritanceMapper {

    @Autowired
    DatabaseSchema databaseSchema;

    @Override
    public <T> InheritanceMapping<T> map(Class<T> clazz) {

        InheritanceMapping<? super T> inheritanceMapping = databaseSchema.getMapping(clazz.getSuperclass());
        // todo add union of existing mapping and current class to create full table
        // think how to add discriminator column only once

        Set<Field> fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        ColumnSchema idField = null;
        Class<? super T> superclass = clazz.getSuperclass();
        while (superclass != null && !superclass.equals(Object.class)) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            idField = getIdField(superclass, new HashSet<>(Arrays.asList(superclass.getDeclaredFields())));
        }

        TableSchema<T> tableSchema = TableSchema.<T> builder()
                .clazz(clazz)
                .tableName(resolveTableName(clazz))
                .columns(createColumnSchemas(fields))
                .idField(idField)
                .build();

        return SingleTableMapping.<T> builder()
                .tableSchema(tableSchema)
                .build();
    }

    private List<String> getDiscriminatorColumnNames(Set<Field> fields) {
        return fields.stream()
                .filter(f -> f.isAnnotationPresent(DiscriminatorValue.class))
                .map(f -> f.getDeclaredAnnotation(DiscriminatorValue.class)
                        .value())
                .collect(Collectors.toList());
    }
}
