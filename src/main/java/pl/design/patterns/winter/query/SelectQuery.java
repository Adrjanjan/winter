package pl.design.patterns.winter.query;

import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectQuery {
    public static <T> String prepareFindById(int id, Class<T> clazz, InheritanceMapping inheritanceMapping)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");

        // z wszystkich pol klasy wybieram tylko te (tylko to jedno) ktore ma adnotacje @Id
        Field fieldWithId = Arrays.stream(clazz.getDeclaredFields()).filter(
                field -> field.getAnnotationsByType(Id.class) != null
        ).collect(Collectors.toList()).get(0);

        //pomocnicza zmienna
        TableSchema tableWithIdField = inheritanceMapping.getTableSchema(fieldWithId.getName());
        //inheritance mapping get tablice ktora ma pole fieldWithId.getName() a nastepnie nazwe tej tablicy do string buildera
        sb.append(tableWithIdField.getTableName());

        sb.append(" WHERE ");
        //nazwa kolumny w tabeli
        sb.append(tableWithIdField.getIdField().getColumnName());
        sb.append(" = ");
        sb.append(id);
        sb.append(";");

        return sb.toString();
    }
}
