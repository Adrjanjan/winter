package pl.design.patterns.winter.inheritance.mapping;

import com.google.common.base.CaseFormat;
import lombok.Data;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class FieldType {

    private String tableName;
    private String columnName;
    private Field field;
    private Method fieldGetter;
    private Method fieldSetter;
    private boolean isId;
    private boolean isGeneratedId;

    public FieldType(Field field, String tableName) {
        this.tableName = tableName;
        this.field = field;
        columnName = extractColumnName(field);
        fieldGetter = findFieldGetter(field);
        fieldSetter = findFieldSetter(field);
        isId = isIdField(field);
        isGeneratedId = isGeneratedIdField(field);
    }

    private static String extractColumnName(Field field) {
        DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        String name = null;
        if (databaseField != null && !databaseField.name().isEmpty()) {
            name = databaseField.name();
        }
        if (name == null) {
            name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        }

        return name;
    }

    private static Method findFieldGetter(Field field) {
        Method fieldGetter = findMethodFromNames(field, true, methodFromField(field, "get"),
                methodFromField(field, "is"));

        if (fieldGetter == null || fieldGetter.getReturnType() != field.getType()) {
            return null;
        }

        return fieldGetter;
    }

    private static Method findFieldSetter(Field field) {
        Method fieldGetter = findMethodFromNames(field, false, methodFromField(field, "set"));
        if (fieldGetter == null || fieldGetter.getReturnType() != field.getType()) {
            return null;
        }

        return fieldGetter;
    }

    private static Method findMethodFromNames(Field field, boolean isGetMethod, String... methodNames) {
        for (String methodName : methodNames) {
            try {
                if (isGetMethod) {
                    // get method has no argument
                    return field.getDeclaringClass().getMethod(methodName);
                }
                else {
                    // set method has same argument type as field
                    return field.getDeclaringClass().getMethod(methodName, field.getType());
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String methodFromField(Field field, String prefix) {
        String name = field.getName();
        String start = name.substring(0, 1);
        start = start.toUpperCase();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(start);
        sb.append(name, 1, name.length());
        return sb.toString();
    }

    private boolean isIdField(Field field) {
        return field.getAnnotation(Id.class) != null;
    }

    private boolean isGeneratedIdField(Field field) {
        Id id = field.getAnnotation(Id.class);

        if (id != null) {
            return id.generated();
        }

        return false;
    }


}
