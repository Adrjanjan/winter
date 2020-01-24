package pl.design.patterns.winter.statements.query;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.NoIdFieldException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.FieldsUtil;

public class SelectQueryBuilder extends QueryBuilder {
    private InheritanceMapping inheritanceMapping;

    Object object;

    Field field;

    TableSchema tableSchema;

    Set<TableSchema> setTableSchema;

    List<Field> fields;

    public SelectQueryBuilder(InheritanceMapping inheritanceMapping)
    {
        this.inheritanceMapping = inheritanceMapping;
        this.query = new StringBuilder();
        this.setTableSchema = new HashSet<>();
        this.fields = new LinkedList<>();
    }

    @Override
    <T> QueryBuilder withObject(T object) {
        this.object = object;
        return this;
    }

    @Override
    QueryBuilder createOperation() {
        query.append("SELECT ");

        fields = FieldsUtil.getAllFieldsInClassHierarchy((Class<?>) object);
        for (Field field : fields) {
            setTableSchema.add(inheritanceMapping.getTableSchema(field.getName()));
        }

        for(TableSchema schema: setTableSchema) {
            for(ColumnSchema colSchema : schema.getColumns()) {
                query.append(schema.getTableName())
                        .append(".")
                        .append(colSchema.getColumnName())
                        .append(", ");
            }
        }

        query.delete(query.length() - 2, query.length() - 1)
                .append(" FROM ");
        return this;
    }

    @Override
    QueryBuilder setTable() {
        field = fields
                .stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList())
                .get(0);

        tableSchema = inheritanceMapping
                .getTableSchema(field.getName());

        query.append(tableSchema.getTableName())
                .append(" ");

        fields.remove(field);

        setTableSchema.clear();
        for (Field field : fields) {
            setTableSchema.add(inheritanceMapping.getTableSchema(field.getName()));
        }

        setTableSchema.remove(tableSchema);

        for(TableSchema schema : setTableSchema) {
            query.append("JOIN ")
                    .append(schema.getTableName())
                    .append(" ON ")
                    .append(tableSchema.getTableName()+"."+tableSchema.getIdField().getColumnName())
                    .append(" = ")
                    .append(schema.getTableName()+"."+schema.getIdField().getColumnName())
                    .append(" ");
        }

        return this;
    }

    @Override
    QueryBuilder setFields() {
        return this;
    }

    @Override
    QueryBuilder setValues() {
        return this;
    }

    @Override
    QueryBuilder withCondition(int id, boolean isConditionSet) {
        if(isConditionSet) {
            query.append(" WHERE ")
                    .append(tableSchema.getTableName())
                    .append(".")
                    .append(tableSchema.getIdField().getColumnName())
                    .append(" = ")
                    .append(id);
        }
        query.append(";");
        return this;
    }

    @Override
    QueryBuilder compose() {
        return this;
    }
}
