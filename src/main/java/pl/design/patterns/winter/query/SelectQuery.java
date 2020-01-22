package pl.design.patterns.winter.query;

import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectQuery {
    public static <T> String prepareFindById(int id, Class<T> clazz, InheritanceMapping inheritanceMapping)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");

        // z wszystkich pol klasy wybieram tylko te (tylko to jedno) ktore ma adnotacje @Id
        Field fieldWithId = Arrays.asList(clazz.getDeclaredFields()).stream().filter(
                field -> field.getAnnotationsByType(Id.class) != null
        ).collect(Collectors.toList()).get(0);

        //inheritance mapping get tablice ktora ma pole fieldWithId.getName() a nastepnie nazwe tej tablicy do string buildera
        sb.append(inheritanceMapping.getTableSchema(fieldWithId.getName()).getTableName());

        sb.append(" WHERE ");
        sb.append(fieldWithId.getName());
        sb.append(" = ");
        sb.append(id);
        sb.append(";");

        return sb.toString();
    }
}
