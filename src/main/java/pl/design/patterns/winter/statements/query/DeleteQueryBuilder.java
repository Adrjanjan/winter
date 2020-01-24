package pl.design.patterns.winter.statements.query;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.FieldsUtil;

import java.lang.reflect.Field;
import java.util.*;

public class DeleteQueryBuilder extends QueryBuilder {

    private InheritanceMapping inheritanceMapping;

    Map<TableSchema, StringBuilder> mapTableSchemaToBuilder;

    Set<TableSchema> setTableSchema;

    Object object;

    List<Field> fields;

    public DeleteQueryBuilder(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
        this.query = new StringBuilder();
        this.mapTableSchemaToBuilder = new HashMap<>();
        this.setTableSchema = new HashSet<>();
        this.fields = new LinkedList<>();
    }

    @Override
    <T> QueryBuilder withObject(T object) {
        this.object = object;
        fields = FieldsUtil.getAllFieldsInClassHierarchy(object.getClass());
        return this;
    }

    @Override
    QueryBuilder createOperation() {
        for (Field field : fields) {
            mapTableSchemaToBuilder.put(inheritanceMapping.getTableSchema(field.getName()), new StringBuilder());
            setTableSchema.add(inheritanceMapping.getTableSchema(field.getName()));
        }
        setTableSchema.forEach(tableSchema -> mapTableSchemaToBuilder.get(tableSchema)
                .append("DELETE FROM "));
        return this;
    }

    @Override
    QueryBuilder setTable() {
        setTableSchema.forEach(tableSchema -> mapTableSchemaToBuilder.get(tableSchema)
                .append(tableSchema.getTableName()));
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
        setTableSchema.forEach(tableSchema -> mapTableSchemaToBuilder.get(tableSchema)
                .append(" WHERE ")
                .append(tableSchema.getIdField().getColumnName())
                .append(" = ")
                .append(id)
                .append(";"));
        return this;
    }


    @Override
    QueryBuilder compose() {
        setTableSchema.forEach(tableSchema -> query.append(mapTableSchemaToBuilder.get(tableSchema)
                .toString())
                .append(" "));
        return this;
    }

}
