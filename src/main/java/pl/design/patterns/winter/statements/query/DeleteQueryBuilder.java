package pl.design.patterns.winter.statements.query;

import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.utils.FieldsUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

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
        fields = FieldsUtil.getAllFieldsInClassHierarchy((Class)object);
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

    //Komentarze analogicznie jak w SelectQuery
//    public static String prepareDelete(int id, Class<?> clazz, InheritanceMapping inheritanceMapping) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("DELETE FROM ");
//
//        Field fieldWithId = Arrays.stream(clazz.getDeclaredFields())
//                .filter(field -> field.isAnnotationPresent(Id.class))
//                .collect(Collectors.toList()).get(0);
//
//        TableSchema tableWithIdField = inheritanceMapping.getTableSchema(fieldWithId.getName());
//        sb.append(tableWithIdField.getTableName());
//
//        sb.append(" WHERE ");
//        //nazwa kolumny w tabeli
//        sb.append(tableWithIdField.getIdField().getColumnName());
//        sb.append(" = ");
//        sb.append(id);
//        sb.append(";");
//
//        // TODO przygotowane pod CLASS_TABLE ale nie jestem pewien czy bd dzialac
//        // Usuwanie odpowiedniego wiersza z tabeli "wyżej". //Wiele tabel wyżej ...? Czy tak może być ...?
//        if(clazz.getAnnotation(DatabaseTable.class).inheritanceType().equals(InheritanceMappingType.CLASS_TABLE)) {
//            Class superClass = clazz.getSuperclass();
//            if(superClass!=Object.class) {
//                Field superClassField = superClass.getDeclaredFields()[0];
//                sb.append(" DELETE FROM ");
//                TableSchema superTable = inheritanceMapping.getTableSchema(superClassField.getName());
//                sb.append(superTable.getTableName());
//                sb.append(" ");
//                sb.append(" WHERE ");
//                //nazwa kolumny w tabeli
//                sb.append(superTable.getIdField().getColumnName());
//                sb.append(" = ");
//                sb.append(id);
//                sb.append(";");
//            }
//        }
//        return sb.toString();
//    }

}
