package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import pl.design.patterns.winter.inheritance.mapping.ConcreteTableMapping;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;

public class ConcreteTableInheritance extends InheritanceMapper {

    @Override
    public <T> InheritanceMapping<T> map(Class<T> clazz) {
        Set<Field> fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        final var idField = getIdField(clazz, fields);

        Class<? super T> superclass = clazz.getSuperclass();

        while (superclass != null && !superclass.equals(Object.class)) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }

        TableSchema<T> tableSchema = TableSchema.<T> builder()
                .clazz(clazz)
                .tableName(resolveTableName(clazz))
                .columns(createColumnSchemas(fields))
                .idField(idField)
                .build();

        return new ConcreteTableMapping<T>(tableSchema);
    }
}
