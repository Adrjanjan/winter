package pl.design.patterns.winter.statements.query;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.NoIdFieldException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.FieldsUtil;

public class SelectQueryBuilder extends QueryBuilder {
    private InheritanceMapping inheritanceMapping;

    Object object;

    Field field;

    TableSchema tableSchema;

    public SelectQueryBuilder(InheritanceMapping inheritanceMapping)
    {
        this.inheritanceMapping = inheritanceMapping;
        this.query = new StringBuilder();
    }

    public String prepareFindAll(Class<?> clazz, InheritanceMapping inheritanceMapping) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");

        // z wszystkich pol klasy wybieram tylko te ktore byly mapowane do BD i biore pierwsze do wyszukania
        this.field = FieldsUtil.getAllFieldsInClassHierarchy(clazz)
                .stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(NoIdFieldException::new);

        //inheritance mapping get tablice ktora ma pole fieldInMapping.getName() a nastepnie nazwe tej tablicy do string buildera
        sb.append(inheritanceMapping.getTableSchema(this.field.getName()).getTableName());

        // TODO Class-Table

        return sb.toString();
    }

    @Override
    <T> QueryBuilder withObject(T object) {
        this.object = object;
        return this;
    }

    @Override
    QueryBuilder createOperation() {
        query.append("SELECT * FROM ");
        return this;
    }

    @Override
    QueryBuilder setTable() {
        field = FieldsUtil.getAllFieldsInClassHierarchy((Class<?>) object)
                .stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList())
                .get(0);

        tableSchema = inheritanceMapping
                .getTableSchema(field.getName());

        query.append(tableSchema.getTableName());
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
            query.append(" WHERE ");
            query.append(tableSchema.getIdField().getColumnName());
            query.append(" = ");
            query.append(id);
        }
        query.append(";");
        return this;
    }

    @Override
    QueryBuilder compose() {
        return this;
    }
}
