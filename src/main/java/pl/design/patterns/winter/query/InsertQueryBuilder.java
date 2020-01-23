package pl.design.patterns.winter.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import pl.design.patterns.winter.exceptions.NonNullableFieldIsNull;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

public class InsertQueryBuilder extends QueryBuilder {

    private InheritanceMapping inheritanceMapping;

    Map<TableSchema, StringBuilder> mapTableSchemaToBuilder;

    Set<TableSchema> setTableSchema;

    Object object;

    List<Field> fields;

    public InsertQueryBuilder(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
        this.query = new StringBuilder();
        this.mapTableSchemaToBuilder = new HashMap<>();
        this.setTableSchema = new HashSet<>();
        this.fields = new LinkedList<>();
    }

    @Override
    <T> QueryBuilder withObject(T object) {
        this.object = object;
        fields = getFieldsToIncludeInQuery(object);
        return this;
    }

    @Override
    QueryBuilder createOperation() {
        for (Field field : fields) {
            mapTableSchemaToBuilder.put(inheritanceMapping.getTableSchema(field.getName()), new StringBuilder());
            setTableSchema.add(inheritanceMapping.getTableSchema(field.getName()));
        }
        setTableSchema.forEach(tableSchema -> mapTableSchemaToBuilder.get(tableSchema)
                .append("INSERT INTO "));

        return this;
    }

    @Override
    QueryBuilder setTable() {
        setTableSchema.forEach(tableSchema -> mapTableSchemaToBuilder.get(tableSchema)
                .append(tableSchema.getTableName())
                .append(" ( "));
        return this;
    }

    @Override
    QueryBuilder setFields() {
        for (var tableSchema : setTableSchema) {
            var stringBuilder = mapTableSchemaToBuilder.get(tableSchema);
            for (ColumnSchema column : tableSchema.getColumns()) {
                stringBuilder.append(column.getColumnName())
                        .append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
            stringBuilder.append(") VALUES ( ");
        }

        return this;
    }

    @Override
    QueryBuilder setValues() throws InvocationTargetException, IllegalAccessException {
        for (var tableSchema : setTableSchema) {
            var stringBuilder = mapTableSchemaToBuilder.get(tableSchema);
            for (ColumnSchema column : tableSchema.getColumns()) {
                Class c = column.getJavaType();
                Object o = column.get(object);

                if ( o == null ) {
                    stringBuilder.append(parseNullableField(object, column));
                } else if ( o.getClass() == String.class ) {
                    stringBuilder.append("\"")
                            .append(c.cast(o)
                                    .toString())
                            .append("\", ");
                } else {
                    stringBuilder.append(o.toString())
                            .append(", ");
                }
            }

            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
            stringBuilder.append(");");
        }
        return this;
    }

    @Override
    QueryBuilder withCondition() {
        return this;
    }

    @Override
    QueryBuilder compose() {
        setTableSchema.forEach(tableSchema -> query.append(mapTableSchemaToBuilder.get(tableSchema)
                .toString())
                .append(" "));
        return this;
    }

    private <T> String parseNullableField(T object, ColumnSchema column) {
        if ( column.isNullable() ) {
            return "NULL, ";
        }
        throw new NonNullableFieldIsNull(String.format("Cannot insert object %s into database.", object.toString()));
    }
}
