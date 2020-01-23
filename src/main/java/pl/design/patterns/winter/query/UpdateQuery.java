
package pl.design.patterns.winter.query;

import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.stream.Collectors;

//UPDATE tabela SET (pole1,pole2,...) = (wartosc1,wartosc2,...) WHERE <rownosc id>;
public class UpdateQuery {
    public static <T> String prepareUpdate(T objectToUpdate, Class<T> clazz, InheritanceMapping inheritanceMapping) throws InvocationTargetException, IllegalAccessException {
        //Class clazz = objectToUpdate.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");

        // wyciaganie nazwy tabeli cz.1
        Field fieldWithId = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList()).get(0);
        // wyciaganie nazwy tabeli cz.2
        TableSchema tableWithIdField = inheritanceMapping.getTableSchema(fieldWithId.getName());
        sb.append(tableWithIdField.getTableName());

        sb.append(" SET (");
        for (Field field : clazz.getFields()) {
            //TODO Update ID?
            //Czy chcemy updatowac Id-ki ...?
            sb.append(field.getName())
                    .append(",");
        }

        //usuwanie ostatniego ","
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") = (");

        //uzyskanie wartosci danych pol
        Object objectForLoop;
        for (ColumnSchema columnSchema : tableWithIdField.getColumns()) {
            //TODO Update ID?
            //updatowanie Id-kow ... ?
            objectForLoop = columnSchema.get(objectToUpdate);
            if (objectForLoop.getClass() == String.class)
                sb.append("\"");
            sb.append(objectForLoop);
            if (objectForLoop.getClass() == String.class)
                sb.append("\"");
            sb.append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(") WHERE ");
        sb.append(tableWithIdField.getIdField().getColumnName());
        sb.append(" = ");
        sb.append(tableWithIdField.getIdField().get(objectToUpdate));
        sb.append(";");

        return sb.toString();
    }
}