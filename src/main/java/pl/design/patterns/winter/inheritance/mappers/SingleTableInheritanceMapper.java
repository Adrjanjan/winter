package pl.design.patterns.winter.inheritance.mappers;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.schemas.TableSchema;

public class SingleTableInheritanceMapper extends InheritanceMapper {

    private DatabaseSchema databaseSchema;

    public SingleTableInheritanceMapper(DatabaseSchema databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    @Override
    <T> InheritanceMapping map(Class<T> clazz) {

        Class<? super T> superclass = clazz.getSuperclass();
        InheritanceMapping inheritanceMapping;
        if ( superclass.equals(Object.class) ) {
            List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
            fields = fields.stream()
                    .filter(f -> !f.getName()
                            .startsWith("this"))
                    .collect(Collectors.toList());

            TableSchema<T> tableSchema = TableSchema.<T> builder()
                    .clazz(clazz)
                    .tableName(resolveTableName(clazz))
                    .columns(createColumnSchemas(fields))
                    .idField(getIdField(fields))
                    .build();

            final Map<String, TableSchema> mapping = new HashMap<>();
            fields.stream()
                    .map(Field::getName)
                    .forEach(name -> mapping.put(name, tableSchema));

            return new InheritanceMapping(mapping);

        } else {
            inheritanceMapping = databaseSchema.getMapping(superclass);
            if ( inheritanceMapping == null ) {
                inheritanceMapping = this.map(superclass);
                databaseSchema.addMapping(superclass, inheritanceMapping);
            }

            List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
            fields = fields.stream()
                    .filter(f -> !f.getName()
                            .startsWith("this"))
                    .collect(Collectors.toList());

            TableSchema tableSchema = inheritanceMapping.getTableSchema(superclass.getFields()[0].getName());

            final Map<String, TableSchema> mapping = new HashMap<>();
            fields.stream()
                    .map(Field::getName)
                    .forEach(name -> mapping.put(name, tableSchema));

            inheritanceMapping.union(mapping);
            return inheritanceMapping;
        }
    }

}
