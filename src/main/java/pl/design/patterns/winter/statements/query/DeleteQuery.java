package pl.design.patterns.winter.statements.query;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;

public class DeleteQuery {

    //Komentarze analogicznie jak w SelectQuery
    public static String prepareDelete(int id, Class<?> clazz, InheritanceMapping inheritanceMapping) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");

        Field fieldWithId = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList()).get(0);

        TableSchema tableWithIdField = inheritanceMapping.getTableSchema(fieldWithId.getName());
        sb.append(tableWithIdField.getTableName());

        sb.append(" WHERE ");
        //nazwa kolumny w tabeli
        sb.append(tableWithIdField.getIdField().getColumnName());
        sb.append(" = ");
        sb.append(id);
        sb.append(";");

        // TODO przygotowane pod CLASS_TABLE ale nie jestem pewien czy bd dzialac
        // Usuwanie odpowiedniego wiersza z tabeli "wyżej". //Wiele tabel wyżej ...? Czy tak może być ...?
        if(clazz.getAnnotation(DatabaseTable.class).inheritanceType().equals(InheritanceMappingType.CLASS_TABLE)) {
            Class superClass = clazz.getSuperclass();
            if(superClass!=Object.class) {
                Field superClassField = superClass.getDeclaredFields()[0];
                sb.append(" DELETE FROM ");
                TableSchema superTable = inheritanceMapping.getTableSchema(superClassField.getName());
                sb.append(superTable.getTableName());
                sb.append(" ");
                sb.append(" WHERE ");
                //nazwa kolumny w tabeli
                sb.append(superTable.getIdField().getColumnName());
                sb.append(" = ");
                sb.append(id);
                sb.append(";");
            }
        }
        return sb.toString();
    }
}
