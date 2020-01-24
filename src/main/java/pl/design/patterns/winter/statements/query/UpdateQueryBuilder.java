
package pl.design.patterns.winter.statements.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.FieldsUtil;

//UPDATE tabela SET (pole1,pole2,...) = (wartosc1,wartosc2,...) WHERE <rownosc id>;
public class UpdateQueryBuilder extends QueryBuilder {
    private InheritanceMapping inheritanceMapping;

    Object object;

    Field field;

    TableSchema tableSchema;

    public UpdateQueryBuilder(InheritanceMapping inheritanceMapping)
    {
        this.inheritanceMapping = inheritanceMapping;
        this.query = new StringBuilder();
    }


//    public static <T> String prepareUpdate(T objectToUpdate, Class<T> clazz, InheritanceMapping inheritanceMapping) {
//        //Class clazz = objectToUpdate.getClass();
//        StringBuilder sb = new StringBuilder();
//        sb.append("UPDATE ");
//
//        // wyciaganie nazwy tabeli cz.1
//        Field fieldWithId = FieldsUtil.getAllFieldsInClassHierarchy(clazz)
//                .stream()
//                .filter(field -> field.isAnnotationPresent(Id.class))
//                .findFirst()
//                .orElseThrow(RuntimeException::new);
//
//        // wyciaganie nazwy tabeli cz.2
//        TableSchema tableWithIdField = inheritanceMapping.getTableSchema(fieldWithId.getName());
//        sb.append(tableWithIdField.getTableName());
//
//        sb.append(" SET (");
//        Object objectForLoop;
//        for (ColumnSchema column : tableWithIdField.getColumns()) {
//            //TODO Update ID?
//            //Czy chcemy updatowac Id-ki ...?
//            try {
//                objectForLoop = column.get(objectToUpdate);
//            } catch (Exception e) {
//                continue;
//            }
//            sb.append(column.getColumnName())
//                    .append(", ");
//        }
//
//        sb.delete(sb.length() - 2, sb.length() - 1);
//        sb.append(") = (");
//
//        //uzyskanie wartosci danych pol
//        for (ColumnSchema columnSchema : tableWithIdField.getColumns()) {
//            //TODO Update ID?
//            //updatowanie Id-kow ... ?
//            try {
//                objectForLoop = columnSchema.get(objectToUpdate);
//            } catch (Exception e) {
//                continue;
//            }
//            if (objectForLoop == null) {
//                sb.append("NULL,");
//                continue;
//            }
//            if (objectForLoop.getClass() == String.class)
//                sb.append("\'");
//            sb.append(objectForLoop);
//            if (objectForLoop.getClass() == String.class)
//                sb.append("\'");
//            sb.append(",");
//        }
//
//        sb.deleteCharAt(sb.length() - 1);
//        sb.append(") WHERE ");
//        sb.append(tableWithIdField.getIdField().getColumnName());
//        sb.append(" = ");
//        sb.append(tableWithIdField.getIdField().get(objectToUpdate));
//        sb.append(";");
//
//        return sb.toString();
//    }

    @Override
    <T> QueryBuilder withObject(T object) {
        this.object = object;
        return this;
    }

    @Override
    QueryBuilder createOperation() {
        query.append("UPDATE ");
        return this;
    }

    @Override
    QueryBuilder setTable() {
        field = FieldsUtil.getAllFieldsInClassHierarchy(object.getClass())
                .stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        tableSchema = inheritanceMapping.getTableSchema(field.getName());
        query.append(tableSchema.getTableName());
        return this;
    }

    @Override
    QueryBuilder setFields() {
        query.append(" SET (");
        Object objectForLoop;
        for (ColumnSchema column : tableSchema.getColumns()) {
            try {
                objectForLoop = column.get(object);
            } catch (Exception e) {
                continue;
            }
            query.append(column.getColumnName())
                    .append(", ");
        }

        query.delete(query.length() - 2, query.length() - 1)
                .append(") = (");
        return this;
    }

    @Override
    QueryBuilder setValues() throws InvocationTargetException, IllegalAccessException {

        Object objectForLoop;
        for (ColumnSchema columnSchema : tableSchema.getColumns()) {
            try {
                objectForLoop = columnSchema.get(object);
            } catch (Exception e) {
                continue;
            }
            if (objectForLoop == null) {
                query.append("NULL,");
                continue;
            }
            if (objectForLoop.getClass() == String.class)
                query.append("\'");
            query.append(objectForLoop);
            if (objectForLoop.getClass() == String.class)
                query.append("\'");
            query.append(",");
        }

        query.deleteCharAt(query.length() - 1);
        return this;
    }

    @Override
    QueryBuilder withCondition(int id, boolean isConditionSet) {
        query.append(") WHERE ");
        query.append(tableSchema.getIdField().getColumnName());
        query.append(" = ");
        query.append(tableSchema.getIdField().get(object));
        query.append(";");
        return this;
    }

    @Override
    QueryBuilder compose() {
        return this;
    }
}