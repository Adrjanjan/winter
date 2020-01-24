package pl.design.patterns.winter.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.NoIdFieldException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.FieldsUtil;

public class SelectQuery extends QueryBuilder {
    public static <T> String prepareFindById(int id, Class<T> clazz, InheritanceMapping inheritanceMapping) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");

        // z wszystkich pol klasy wybieram tylko te (tylko to jedno) ktore ma adnotacje @Id
        Field fieldWithId = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList())
                .get(0);

        // pomocnicza zmienna
        TableSchema tableWithIdField = inheritanceMapping.getTableSchema(fieldWithId.getName());
        // inheritance mapping get tablice ktora ma pole fieldWithId.getName() a nastepnie nazwe tej tablicy do string buildera
        sb.append(tableWithIdField.getTableName());

        // TODO przygotowane pod CLASS_TABLE ale nie jestem pewien czy bd dzialac
        // if(clazz.getAnnotation(DatabaseTable.class).equals(InheritanceMappingType.CLASS_TABLE)) {
        // sb.append(" JOIN ");
        // sb.append(inheritanceMapping.getTableSchema(clazz.getSuperclass().getName()).getTableName());
        // sb.append(" ");
        // }
        sb.append(" WHERE ");
        // nazwa kolumny w tabeli
        sb.append(tableWithIdField.getIdField()
                .getColumnName());
        sb.append(" = ");
        sb.append(id);
        sb.append(";");

        return sb.toString();
    }

    public String prepareFindAll(Class<?> clazz, InheritanceMapping inheritanceMapping) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");

        // z wszystkich pol klasy wybieram tylko te ktore byly mapowane do BD i biore pierwsze do wyszukania
        Field fieldInMapping = FieldsUtil.getAllFieldsInClassHierarchy(clazz)
                .stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(NoIdFieldException::new);

        // inheritance mapping get tablice ktora ma pole fieldInMapping.getName() a nastepnie nazwe tej tablicy do string buildera
        sb.append(inheritanceMapping.getTableSchema(fieldInMapping.getName())
                .getTableName());

        // TODO Class-Table

        return sb.toString();
    }

    @Override
    <T> QueryBuilder withObject(T object) {
        return null;
    }

    @Override
    QueryBuilder createOperation() {
        return null;
    }

    @Override
    QueryBuilder setTable() {
        return null;
    }

    @Override
    QueryBuilder setFields() {
        return null;
    }

    @Override
    QueryBuilder setValues() throws InvocationTargetException, IllegalAccessException {
        return null;
    }

    @Override
    QueryBuilder withCondition() {
        return null;
    }

    @Override
    QueryBuilder compose() {
        return null;
    }
}
